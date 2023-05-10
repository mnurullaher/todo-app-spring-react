package com.nurullah.controller;

import com.nurullah.dto.LoginRequest;
import com.nurullah.dto.LoginResponse;
import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;
import com.nurullah.service.CustomerService;
import com.nurullah.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    public final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        return new LoginResponse(jwtTokenService.createToken(loginRequest.getUsername()));
    }

    @PostMapping("/signup")
    public Customer signup(@RequestBody SignupRequest signupRequest) {
        return customerService.saveCustomer(
                signupRequest.getFullName(),
                signupRequest.getUsername(),
                signupRequest.getPassword()
        );
    }
}
