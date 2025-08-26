package com.example.news_project.service;

import com.example.news_project.dto.api_response.ResponseApi;
import com.example.news_project.dto.auth_user.AuthUserDto;
import com.example.news_project.dto.auth_user.ResponseLoginDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.enums.AppRoleName;
import com.example.news_project.repository.AuthUserRepository;
import com.example.news_project.repository.RoleRepository;
import com.example.news_project.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.SecureRandom;
import java.util.Random;

@Service
//@Transactional
public class AuthService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Lazy
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    EmailService emailService;
    @Autowired
    JwtService jwtService;

    public AuthService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional
    public ResponseApi register(@RequestBody AuthUserDto authUserDto) throws MessagingException {
        if (!authUserRepository.existsByEmail(authUserDto.getEmail())) {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(authUser.getUsername());
            authUser.setFullName(authUserDto.getFullName());
            authUser.setPassword(passwordEncoder.encode(authUserDto.getPassword()));
            authUser.setRole(roleRepository.findByAppRoleName(AppRoleName.AUTHOR).orElseThrow());
            authUser.setEmail(authUserDto.getEmail());
            authUser.setEmailCode(String.valueOf(new Random().nextInt(10000, 99999)));
            authUserRepository.save(authUser);
            boolean b = emailService.sendMail(authUser.getEmail(), authUser.getEmailCode());
            if (b) {
                return new ResponseApi(200, "created");
            } else {
                return new ResponseApi(409, "something went wrong");
            }
        }
        return new ResponseApi(409, "email exists");
    }

    public boolean activate(String code, String email) {
        AuthUser authUser = authUserRepository.findByEmailAndEmailCode(email, code).orElse(null);
        if (authUser == null) {
            return false;
        }
        authUser.setEnabled(true);
        authUser.setEmailCode(null);
        authUserRepository.save(authUser);
        return true;
    }

    public ResponseLoginDto login(String password, String email) {

        try {
            AuthUser principal = (AuthUser) authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)).getPrincipal();
            String s = jwtService.generateToken(principal.getUsername());
            return new ResponseLoginDto(principal.getUsername(), principal.getFullName(), principal.getEmail(), s, principal.getRole().getAppRoleName());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " - not found"));
    }

    public ResponseApi resetPassword(String email) {
        AuthUser authUser = authUserRepository.findByEmail(email).orElse(null);
        if (authUser == null)
            return new ResponseApi(404, "email not found");
        int i = new SecureRandom().nextInt(10000, 99999);
        try {
            emailService.sendMail(authUser.getEmail(), String.valueOf(i));
            authUser.setEmailCode(String.valueOf(i));
            authUserRepository.save(authUser);
            return new ResponseApi(200, " we sent email code");
        } catch (MessagingException e) {
            return new ResponseApi(409, "something went wrong");
        }
    }

    public ResponseApi resetPasswordActivation(String code, String email, String password) {
        AuthUser authUser = authUserRepository.findByEmail(email).orElse(null);
        if (authUser == null)
            return new ResponseApi(404, "email not found");
        if (!authUser.getEmailCode().equals(code))
            return new ResponseApi(400, "code is incorrect");
        authUser.setEmailCode(null);
        authUser.setPassword(passwordEncoder.encode(password));
        authUserRepository.save(authUser);
        return new ResponseApi(200, "password is changed");
    }

    public ResponseApi deleteUser(AuthUser authUser) {
        authUserRepository.deleteById(authUser.getId());
        return new ResponseApi(204, "User delete successfully");
    }
}