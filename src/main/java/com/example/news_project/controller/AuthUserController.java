package com.example.news_project.controller;

import com.example.news_project.dto.auth_user.UpdateProfileDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.service.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authuser")
public class AuthUserController {
    @Autowired
    AuthUserService authUserService;

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@Valid  @RequestBody UpdateProfileDto updateProfileDto, @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(authUserService.edit(updateProfileDto, authUser));
    }

}