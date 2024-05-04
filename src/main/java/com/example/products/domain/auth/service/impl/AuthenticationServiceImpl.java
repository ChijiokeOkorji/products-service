package com.example.products.domain.auth.service.impl;

import com.example.products.domain.auth.ApiKeyAuthentication;
import com.example.products.domain.auth.service.AuthenticationService;
import com.example.products.infrastructure.entity.AuthEntity;
import com.example.products.infrastructure.repository.AuthRepository;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(String token) {
        try {
            if (token.startsWith("FLW")) {
                // Authenticate API Key
                AuthEntity authEntity = authRepository.findByApiKey(token);

                if (Objects.isNull(authEntity)) {
                    // Handle unexpected claim type
                    throw new IllegalArgumentException("Invalid API Key");
                } else {
                    Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + authEntity.getRole()));
                    return new ApiKeyAuthentication(token, authorities);
                }
            } else {
                // Authenticate Keycloak Access Token
                Jwt jwt = jwtDecoder.decode(token);

                // Extract user roles from JWT claims
                Collection<SimpleGrantedAuthority> roles = extractKeyCloakRoles(jwt);

                // Create an Authentication object with user roles
                return new JwtAuthenticationToken(jwt, roles);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Collection<SimpleGrantedAuthority> extractKeyCloakRoles(Jwt jwt) {
        if (jwt.getClaim("realm_access") instanceof LinkedTreeMap<?,?>) {
            LinkedTreeMap<String, List<String>> clientRoleMap = jwt.getClaim("realm_access");
            List<String> clientRoles = new ArrayList<>(clientRoleMap.get("roles"));

            return clientRoles.stream()
                    .map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            // Handle unexpected claim type
            throw new IllegalArgumentException("Unexpected claim type for 'realm_access'");
        }
    }
}
