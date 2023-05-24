package com.nurullah.controller;

import com.nurullah.dto.LoginRequest;
import com.nurullah.dto.SignupRequest;
import com.nurullah.repository.CustomerRepository;
import com.nurullah.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerIT {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    public void should_authenticate_and_return_token() throws Exception {
        var username = "nurullaher";
        var password = "12345";
        var loginRequest = new LoginRequest(username, password);
        customerService.saveCustomer(
                new SignupRequest("Nurullah Er", username, password)
        );

        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasKey("token")));
    }

    @Test
    public void should_save_and_return_customer() throws Exception {
        var fullname = "Nurullah Er";
        var username = "nurullaher";
        var password = "12345";
        var signupRequest = new SignupRequest(fullname, username, password);

        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.fullName", Matchers.is(fullname)))
                .andExpect(jsonPath("$.username", Matchers.is(username)));

        then(customerService.findByUserName(username)).isNotNull();
    }
}
