package com.example.news_project.service;

import com.example.news_project.dto.auth_user.ResponseAuthUserDto;
import com.example.news_project.dto.auth_user.UpdateProfileDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ResponseAuthUserDto edit(UpdateProfileDto updateProfileDto, AuthUser authUser){
        authUser.setUsername(updateProfileDto.getUsername());
        authUser.setFullName(updateProfileDto.getFullName());
        authUser.setPassword(passwordEncoder.encode(updateProfileDto.getPassword()));
        AuthUser save = authUserRepository.save(authUser);
        return new ResponseAuthUserDto(save.getUsername(), save.getFullName(), save.getEmail());
    }

}