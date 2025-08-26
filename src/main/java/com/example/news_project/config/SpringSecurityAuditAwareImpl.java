package com.example.news_project.config;

import com.example.news_project.entity.AuthUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<AuthUser> {
    @Override
    public Optional<AuthUser> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of( ((AuthUser)authentication.getPrincipal()));
        }
        return Optional.empty();
    }
}