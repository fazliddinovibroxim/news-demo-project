package com.example.news_project.repository;

import com.example.news_project.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    boolean existsByEmail(String email);
    Optional<AuthUser> findByEmail(String email);

    Optional<AuthUser> findByEmailAndEmailCode(String email, String emailCode);
    boolean existsById(UUID id);

    Optional<Object> findByResetToken(String token);
}