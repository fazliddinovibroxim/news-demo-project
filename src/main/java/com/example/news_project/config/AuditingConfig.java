package com.example.news_project.config;

import com.example.news_project.entity.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    AuditorAware<AuthUser> integerAuditorAware(){
        return new SpringSecurityAuditAwareImpl();
    }
}