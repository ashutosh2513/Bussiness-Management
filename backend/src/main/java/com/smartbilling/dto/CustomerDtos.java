package com.smartbilling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerDtos {
    public record CreateCustomerRequest(
            @NotBlank(message = "Name is required")
            String name,
            @NotBlank(message = "Phone is required")
            @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be exactly 10 digits")
            String phone,
            String email,
            String gstNumber,
            String address,
            @NotNull(message = "Retailer ID is required")
            UUID retailerId
    ) {}

    public record CustomerResponse(
            UUID id,
            String name,
            String phone,
            String email,
            LocalDate createdAt
    ) {}
}
