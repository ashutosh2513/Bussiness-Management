package com.smartbilling.web;

import com.smartbilling.dto.CustomerDtos;
import com.smartbilling.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add")
    public CustomerDtos.CustomerResponse createCustomer(
            @Valid @RequestBody CustomerDtos.CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PostMapping("/get")
    public List<CustomerDtos.CustomerListItem> getCustomers(
            @RequestBody(required = false) CustomerDtos.GetCustomersRequest request) {
        return customerService.getCustomers(request);
    }
}
