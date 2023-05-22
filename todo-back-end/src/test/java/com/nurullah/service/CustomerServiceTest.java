package com.nurullah.service;

import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CustomerService customerService;


    @Test
    public void when_saveCustomerWithValidRequest_itShouldReturnCustomer() {
        SignupRequest signupRequest = SignupRequest.builder()
                .fullName("Nurullah ER")
                .username("nurullaher")
                .password("12345")
                .build();

        given(passwordEncoder.encode("12345")).willReturn("encrypted password");

        doAnswer(x -> {
            var customer =  (Customer) x.getArgument(0);
            customer.setId(1);
            return customer;
        }).when(customerRepository).save(any(Customer.class));

        Customer result = customerService.saveCustomer(signupRequest);

        then(result).isNotNull();
        then(result.getId()).isEqualTo(1);
        then(result.getUsername()).isEqualTo("nurullaher");
        then(result.getPassword()).isEqualTo("encrypted password");
        then(result.getFullName()).isEqualTo("Nurullah ER");
    }

    @Test
    public void when_findByUsernameWithValidUsername_itShouldReturnCustomer() {
        String username = "nurullaher";

        var customer = Customer.builder()
                .id(1)
                .fullName("Nurullah ER")
                .username(username)
                .password("12345")
                .todos(List.of(new Todo()))
                .build();

        given(customerRepository.findByUsername(username)).willReturn(customer);

        Customer result = customerService.findByUserName(username);

        then(result).isEqualTo(customer);
    }
}