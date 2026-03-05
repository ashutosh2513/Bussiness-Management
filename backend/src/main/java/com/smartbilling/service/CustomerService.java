package com.smartbilling.service;

import com.smartbilling.dto.CustomerDtos;

import java.util.List;

public interface CustomerService {
    CustomerDtos.CustomerResponse createCustomer(CustomerDtos.CreateCustomerRequest request);
    List<CustomerDtos.CustomerListItem> getCustomers(CustomerDtos.GetCustomersRequest request);
}
