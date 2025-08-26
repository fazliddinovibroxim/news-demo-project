package com.example.news_project.repository;

import com.example.news_project.entity.Role;
import com.example.news_project.enums.AppRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByAppRoleName(AppRoleName name);
}