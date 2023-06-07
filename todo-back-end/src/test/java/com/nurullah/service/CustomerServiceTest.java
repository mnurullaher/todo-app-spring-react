package com.nurullah.service;

import com.nurullah.model.Customer;
import com.nurullah.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.nurullah.utils.CustomerTestUtils.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void should_save_and_return_customer() {
        given(passwordEncoder.encode(PASSWORD)).willReturn("encrypted password");

        doAnswer(x -> {
            var customer = (Customer) x.getArgument(0);
            customer.setId(1);
            return customer;
        }).when(customerRepository).save(any(Customer.class));

        var result = customerService.saveCustomer(getMockSignupRequest());

        then(result).isNotNull();
        then(result.getId()).isEqualTo(1);
        then(result.getUsername()).isEqualTo(USERNAME);
        then(result.getPassword()).isEqualTo("encrypted password");
        then(result.getFullName()).isEqualTo(FULLNAME);
    }

    @Test
    public void should_return_customer_with_given_username() {
        given(customerRepository.findByUsername(USERNAME)).willReturn(getMockCustomer());

        var result = customerService.findByUserName(USERNAME);

        verify(customerRepository).findByUsername(USERNAME);
        then(result).isEqualTo(getMockCustomer());
    }
}