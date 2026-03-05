package com.smartbilling.service;

import com.smartbilling.common.AuditableAction;
import com.smartbilling.domain.Customer;
import com.smartbilling.domain.User;
import com.smartbilling.dto.CustomerDtos;
import com.smartbilling.repository.CustomerRepository;
import com.smartbilling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User retailer = getLoggedInUser();

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

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to resolve logged-in user");
        }

        return userRepository.findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to resolve logged-in user"));
    }
}
