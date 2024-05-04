package com.example.products.infrastructure.repository;

import com.example.products.infrastructure.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, UUID> {
    AuthEntity findByApiKey(String apiKey);
}
