package com.nurullah.security;

import com.nurullah.repository.CustomerRepository;
import com.nurullah.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();
    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var customer = customerService.findByUserName(username);
        return new  User(username, "{noop}"+ customer.getPassword(), List.of());
    }
}
