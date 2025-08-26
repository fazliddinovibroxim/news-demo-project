package com.example.news_project.dto.auth_user;

import com.example.news_project.enums.AdsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseAuthUserDto {
    private String username;
    private String fullName;
    private String email;
}