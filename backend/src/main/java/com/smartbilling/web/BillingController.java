package com.smartbilling.web;

import com.smartbilling.domain.Customer;
import com.smartbilling.dto.BillingDtos;
import com.smartbilling.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;

    @GetMapping("/customers/search")
    public List<Customer> customerSearch(@RequestParam(name = "q", required = false) String q) {
        return billingService.searchCustomers(q);
    }

    @PostMapping("/invoices")
    public BillingDtos.InvoiceSummary createInvoice(@Valid @RequestBody BillingDtos.CreateInvoiceRequest request) {
        return billingService.createInvoice(request);
    }

    @PostMapping("/payments")
    public void createPayment(@Valid @RequestBody BillingDtos.PaymentRequest request) {
        billingService.createPayment(request);
    }

    @GetMapping("/dashboard")
    public BillingDtos.DashboardDto dashboard() {
        return billingService.dashboard();
    }
}
