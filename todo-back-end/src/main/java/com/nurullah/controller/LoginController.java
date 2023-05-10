package com.nurullah.controller;

import com.nurullah.dto.LoginRequest;
import com.nurullah.dto.LoginResponse;
import com.nurullah.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    public final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        return new LoginResponse(jwtTokenService.createToken(loginRequest.getUsername()));
    }
}
