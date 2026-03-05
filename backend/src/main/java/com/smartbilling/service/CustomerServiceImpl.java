package com.smartbilling.service;

import com.smartbilling.common.AuditableAction;
import com.smartbilling.domain.Customer;
import com.smartbilling.domain.Enums.Role;
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
import java.util.List;
import java.util.UUID;

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
                saved.getCrtDt().atZone(ZoneOffset.UTC).toLocalDate());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDtos.CustomerListItem> getCustomers(CustomerDtos.GetCustomersRequest request) {

        UUID retailerId = null;
        User loggedInUser = getLoggedInUser();
        if (request != null && request.id() != null) {
            if (loggedInUser.getRole() != (Role.ADMIN)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You are not authorized to access this resource");
            }
            retailerId = request.id();
        } else {
            retailerId = loggedInUser.getId();
        }

        return customerRepository.findByRetailerId(retailerId).stream()
                .map(customer -> new CustomerDtos.CustomerListItem(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getGstNumber(),
                        customer.getAddress(),
                        customer.getCrtDt().atZone(ZoneOffset.UTC).toLocalDate()))
                .toList();
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to resolve logged-in user");
        }

        return userRepository.findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to resolve logged-in user"));
    }
}
