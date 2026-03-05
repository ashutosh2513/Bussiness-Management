package com.smartbilling.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ProductDtos {
    public record CreateProductRequest(
            @NotBlank(message = "Name is required")
            String name,
            @NotNull(message = "Category ID is required")
            UUID categoryId,
            @NotNull(message = "Unit ID is required")
            UUID unitId,
            @NotNull(message = "Purchase price is required")
            @DecimalMin(value = "0.0", inclusive = true, message = "Purchase price must be non-negative")
            Double purchasePrice,
            @NotNull(message = "Selling price is required")
            @DecimalMin(value = "0.0", inclusive = true, message = "Selling price must be non-negative")
            Double sellingPrice,
            @NotNull(message = "GST percent is required")
            @DecimalMin(value = "0.0", inclusive = true, message = "GST percent must be non-negative")
            Double gstPercent,
            @NotBlank(message = "SKU code is required")
            String skuCode,
            @NotNull(message = "Retailer ID is required")
            UUID retailerId
    ) {}

    public record ProductResponse(
            UUID id,
            String name,
            UUID categoryId,
            UUID unitId,
            Double purchasePrice,
            Double sellingPrice,
            Double gstPercent,
            String skuCode,
            UUID retailerId,
            LocalDate createdAt
    ) {}

    public record GetProductsRequest(
            UUID id
    ) {}

    public record ProductListItem(
            UUID id,
            String name,
            UUID categoryId,
            UUID unitId,
            Double purchasePrice,
            Double sellingPrice,
            Double gstPercent,
            String skuCode,
            UUID retailerId,
            LocalDate createdAt
    ) {}
}
