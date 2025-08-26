package com.example.news_project.component;

import com.example.news_project.entity.AuthUser;
import com.example.news_project.entity.Role;
import com.example.news_project.enums.AppPermissions;
import com.example.news_project.enums.AppRoleName;
import com.example.news_project.repository.AuthUserRepository;
import com.example.news_project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthUserRepository authUserRepository;

    @Value(value = "${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) {

        if (mode.equals("always")) {
            Role admin = new Role();
            admin.setAppRoleName(AppRoleName.ADMIN);
            admin.setAppPermissions(Set.of(AppPermissions.values()));
            Role saveAdmin = roleRepository.save(admin);

            AuthUser authUserAdmin = new AuthUser();
            authUserAdmin.setEnabled(true);
            authUserAdmin.setEmail("admin@gmail.com");
            authUserAdmin.setRole(saveAdmin);
            authUserAdmin.setPassword(passwordEncoder.encode("admin"));
            authUserAdmin.setFullName("admin");
            authUserAdmin.setUsername("main_admin");
            authUserRepository.save(authUserAdmin);

            Role user = new Role();
            user.setAppRoleName(AppRoleName.AUTHOR);
            roleRepository.save(user);
        }

    }
}