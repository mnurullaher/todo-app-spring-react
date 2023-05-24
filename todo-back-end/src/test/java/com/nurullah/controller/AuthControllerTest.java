package com.nurullah.controller;

import com.nurullah.dto.LoginRequest;
import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;
import com.nurullah.service.CustomerService;
import com.nurullah.service.JwtTokenService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenService jwtTokenService;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_authenticate_and_return_token() throws Exception {
        var username = "nurullaher";
        var password = "12345";
        var loginRequest = new LoginRequest(username, password);

        mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        verify(jwtTokenService).createToken(username);
    }

    @Test
    public void should_return_customer() throws Exception {
        var fullname = "Nurullah Er";
        var username = "nurullaher";
        var password = "12345";
        var signupRequest = new SignupRequest(fullname, username, password);

        given(customerService.saveCustomer(signupRequest)).willReturn(
                new Customer(1, fullname, username, password, List.of())
        );

        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.fullName", Matchers.is(fullname)))
                .andExpect(jsonPath("$.username", Matchers.is(username)));
    }

}