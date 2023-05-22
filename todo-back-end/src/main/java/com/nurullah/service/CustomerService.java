package com.nurullah.service;

import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;
import com.nurullah.repository.CustomerRepository;
import com.nurullah.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public Customer saveCustomer(SignupRequest signupRequest) {

        Customer newCustomer = Customer.builder()
                .fullName(signupRequest.getFullName())
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        return customerRepository.save(newCustomer);
    }

    public Customer findByUserName(String username) {
        return customerRepository.findByUsername(username);
    }

}
