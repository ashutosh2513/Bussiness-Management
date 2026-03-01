package com.smartbilling.service;

import com.smartbilling.dto.CustomerDtos;

public interface CustomerService {
    CustomerDtos.CustomerResponse createCustomer(CustomerDtos.CreateCustomerRequest request);
}
