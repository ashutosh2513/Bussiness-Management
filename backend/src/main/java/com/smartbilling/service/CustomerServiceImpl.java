package com.smartbilling.service;

import com.smartbilling.common.AuditableAction;
import com.smartbilling.domain.Customer;
import com.smartbilling.domain.User;
import com.smartbilling.dto.CustomerDtos;
import com.smartbilling.repository.CustomerRepository;
import com.smartbilling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @AuditableAction("CUSTOMER_CREATE")
    public CustomerDtos.CustomerResponse createCustomer(CustomerDtos.CreateCustomerRequest request) {
        User retailer = userRepository.findById(request.retailerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid retailerId"));

        Customer customer = new Customer();
        customer.setName(request.name().trim());
        customer.setPhone(request.phone().trim());
        customer.setEmail(request.email());
        customer.setGstNumber(request.gstNumber());
        customer.setAddress(request.address());
        customer.setRetailer(retailer);

        Customer saved = customerRepository.save(customer);

        return new CustomerDtos.CustomerResponse(
                saved.getId(),
                saved.getName(),
                saved.getPhone(),
                saved.getEmail(),
                saved.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDate()
        );
    }
}
