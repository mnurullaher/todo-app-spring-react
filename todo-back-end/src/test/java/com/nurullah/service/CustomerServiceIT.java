package com.nurullah.service;

import com.nurullah.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.nurullah.utils.CustomerTestUtils.*;
import static org.assertj.core.api.BDDAssertions.then;


@Testcontainers
@SpringBootTest
public class CustomerServiceIT {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    public void should_save_and_return_customer() {
        var result = customerService.saveCustomer(getMockSignupRequest());

        then(result).isNotNull();
        then(result.getFullName()).isEqualTo(FULLNAME);
        then(result.getUsername()).isEqualTo(USERNAME);
        then(result.getId()).isNotEqualTo(0);
    }

    @Test
    public void should_return_customer_with_given_username() {
        customerService.saveCustomer(getMockSignupRequest());

        var result1 = customerService.findByUserName(USERNAME);

        then(result1).isNotNull();
        then(result1.getFullName()).isEqualTo(FULLNAME);
    }
}
