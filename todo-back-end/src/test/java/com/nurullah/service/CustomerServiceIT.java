package com.nurullah.service;

import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;
import com.nurullah.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;


@Testcontainers
@SpringBootTest
public class CustomerServiceIT {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

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

    @Test
    public void test_saveCustomer() {
        var result =
                customerService.saveCustomer(
                        new SignupRequest("Nurullah Er", "nurullaher", "12345")
                );

        then(result).isNotNull();
        then(result.getFullName()).isEqualTo("Nurullah Er");
        then(result.getUsername()).isEqualTo("nurullaher");
        then(result.getId()).isNotEqualTo(0);
    }

    @Test
    public void test_findByUsername() {
        customerRepository.saveAll(
            List.of(
                new Customer(1, "Nurullah Er", "nurullaher", "12345", List.of()),
                new Customer(2, "Serkan Erip", "serkanerip", "12345", List.of()),
                new Customer(3, "Bedirhan Catal", "bedircatal", "12345", List.of())
            )
        );

        var result1 = customerService.findByUserName("nurullaher");
        var result2 = customerService.findByUserName("serkanerip");
        var result3 = customerService.findByUserName("bedircatal");

        then(result1).isNotNull();
        then(result2).isNotNull();
        then(result3).isNotNull();

        then(result1.getFullName()).isEqualTo("Nurullah Er");
        then(result2.getFullName()).isEqualTo("Serkan Erip");
        then(result3.getFullName()).isEqualTo("Bedirhan Catal");

        then(result1.getId()).isEqualTo(1);
        then(result2.getId()).isEqualTo(2);
        then(result3.getId()).isEqualTo(3);
    }
}
