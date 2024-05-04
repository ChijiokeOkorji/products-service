package com.example.products.domain.auth.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication authenticate(String token);
}
