package com.example.products.domain.auth;

import com.example.products.domain.auth.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            Authentication authentication = authenticationService.authenticate(token);

            if (authentication != null && authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
