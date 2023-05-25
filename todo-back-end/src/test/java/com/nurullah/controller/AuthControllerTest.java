package com.nurullah.controller;

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

import static com.nurullah.utils.CustomerTestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(getMockLoginRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)
        );
        verify(jwtTokenService).createToken(USERNAME);
    }

    @Test
    public void should_return_customer() throws Exception {
        var signupRequest = getMockSignupRequest();

        given(customerService.saveCustomer(signupRequest)).willReturn(getMockCustomer());

        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.fullName", Matchers.is(FULLNAME)))
                .andExpect(jsonPath("$.username", Matchers.is(USERNAME)));
    }

}