package com.nurullah.service;

import com.nurullah.model.Customer;
import com.nurullah.repository.CustomerRepository;
import com.nurullah.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(String fullName, String username, String password) {

        Customer newCustomer = Customer.builder()
                .fullName(fullName)
                .username(username)
                .password(password)
                .build();

        return customerRepository.save(newCustomer);
    }

}
