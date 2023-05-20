package com.nurullah.security;

import com.nurullah.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = headerValue.replace("Bearer ", "");
        Claims claims = jwtTokenService.getAllClaimsFromToken(token);

        var username = claims.get("username");

        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                username,
                null,
                List.of()
        ));
        SecurityContextHolder.setContext(newContext);

        filterChain.doFilter(request, response);
    }
}
