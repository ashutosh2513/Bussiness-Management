package com.smartbilling.service;

import com.smartbilling.domain.*;
import com.smartbilling.dto.BillingDtos;
import com.smartbilling.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final LedgerRepository ledgerRepository;
    private final PaymentRepository paymentRepository;
    private final InventoryMovementRepository inventoryMovementRepository;

    @Transactional
    public BillingDtos.InvoiceSummary createInvoice(BillingDtos.CreateInvoiceRequest request) {
        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceNo(nextInvoiceNo());

        double subtotal = 0.0;
        double tax = 0.0;
        for (BillingDtos.ProductLineDto line : request.items()) {
            Product product = productRepository.findById(line.productId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
            InvoiceItem item = new InvoiceItem();
            item.setInvoice(invoice);
            item.setProduct(product);
            item.setQuantity(line.quantity());
            item.setUnitPrice(product.getPrice());
            item.setTaxRate(product.getTaxRate());
            item.setLineTotal((product.getPrice() * line.quantity()) * (1 + product.getTaxRate() / 100));
            subtotal += product.getPrice() * line.quantity();
            tax += (product.getPrice() * line.quantity()) * (product.getTaxRate() / 100);
            invoice.getItems().add(item);

            InventoryMovement movement = new InventoryMovement();
            movement.setProduct(product);
            movement.setQuantity(-line.quantity());
            movement.setMovementType("STOCK_OUT");
            movement.setNote("Sold in invoice " + invoice.getInvoiceNo());
            inventoryMovementRepository.save(movement);
        }

        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(tax);
        invoice.setGrandTotal(subtotal + tax);
        invoice.setPaidAmount(request.paidAmount());
        invoiceRepository.save(invoice);

        postLedger(invoice, request.paidAmount());
        if (request.paidAmount() > 0 && request.paymentMethod() != null) {
            createPayment(new BillingDtos.PaymentRequest(invoice.getId(), request.paymentMethod(), request.paidAmount(), "AUTO"));
        }
        return new BillingDtos.InvoiceSummary(invoice.getId(), invoice.getInvoiceNo(), invoice.getGrandTotal(), invoice.getPaidAmount(), invoice.getGrandTotal() - invoice.getPaidAmount());
    }

    @Transactional
    public void createPayment(BillingDtos.PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.invoiceId()).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        invoice.setPaidAmount(invoice.getPaidAmount() + request.amount());
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setMethod(request.method());
        payment.setAmount(request.amount());
        payment.setReferenceNo(request.referenceNo());
        paymentRepository.save(payment);

        LedgerEntry credit = new LedgerEntry();
        credit.setCustomer(invoice.getCustomer());
        credit.setInvoice(invoice);
        credit.setType(Enums.LedgerType.CREDIT);
        credit.setAmount(request.amount());
        credit.setNarration("Payment received");
        ledgerRepository.save(credit);

        Customer customer = invoice.getCustomer();
        customer.setOutstandingBalance(Math.max(0.0, customer.getOutstandingBalance() - request.amount()));
        customerRepository.save(customer);
    }

    public List<Customer> searchCustomers(String query) {
        return customerRepository.findByNameContainingIgnoreCase(query == null ? "" : query);
    }

    public BillingDtos.DashboardDto dashboard() {
        List<Invoice> invoices = invoiceRepository.findAll();
        double revenue = invoices.stream().mapToDouble(Invoice::getGrandTotal).sum();
        double outstanding = invoices.stream().mapToDouble(i -> i.getGrandTotal() - i.getPaidAmount()).sum();
        List<String> topItems = invoices.stream().flatMap(i -> i.getItems().stream())
                .collect(java.util.stream.Collectors.groupingBy(it -> it.getProduct().getName(), java.util.stream.Collectors.summingInt(InvoiceItem::getQuantity)))
                .entrySet().stream().sorted(java.util.Map.Entry.<String,Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(5).map(java.util.Map.Entry::getKey).toList();
        long lowStock = 0; // Placeholder to be implemented with stock snapshot table
        return new BillingDtos.DashboardDto(revenue, outstanding, lowStock, topItems);
    }

    private void postLedger(Invoice invoice, double paidAmount) {
        LedgerEntry debit = new LedgerEntry();
        debit.setCustomer(invoice.getCustomer());
        debit.setInvoice(invoice);
        debit.setType(Enums.LedgerType.DEBIT);
        debit.setAmount(invoice.getGrandTotal());
        debit.setNarration("Invoice raised");
        ledgerRepository.save(debit);

        Customer customer = invoice.getCustomer();
        customer.setOutstandingBalance(customer.getOutstandingBalance() + invoice.getGrandTotal() - paidAmount);
        customerRepository.save(customer);
    }

    private String nextInvoiceNo() {
        int last = invoiceRepository.findTopByOrderByCreatedAtDesc()
                .map(i -> i.getInvoiceNo().replaceAll("\\D", ""))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .orElse(0);
        return "INV-" + String.format("%05d", last + 1);
    }
}
