package com.smartbilling.dto;

import com.smartbilling.domain.Enums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class BillingDtos {
    public record CustomerDto(UUID id, String name, String phone, String gstNumber, String address, double outstandingBalance) {}
    public record ProductLineDto(@NotNull UUID productId, @Min(1) int quantity) {}
    public record CreateInvoiceRequest(@NotNull UUID customerId, @NotNull List<ProductLineDto> items,
                                       Enums.PaymentMethod paymentMethod, double paidAmount) {}
    public record InvoiceSummary(UUID id, String invoiceNo, double grandTotal, double paidAmount, double dueAmount) {}
    public record PaymentRequest(@NotNull UUID invoiceId, @NotNull Enums.PaymentMethod method, @Min(1) double amount, String referenceNo) {}
    public record DashboardDto(double todayRevenue, double outstandingDues, long lowStockItems, List<String> topItems) {}
}
