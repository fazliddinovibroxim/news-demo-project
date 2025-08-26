package com.example.news_project.dto.auth_user;

import com.example.news_project.enums.AppRoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseLoginDto {
    private String username;
    private String fullName;
    private String email;
    private String token;
    private AppRoleName appRole;
}