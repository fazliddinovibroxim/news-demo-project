package com.example.news_project.dto.auth_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthUserDto {

    @NotBlank
    @Size(min = 4, max = 25)
    private String username;
    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;
    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
}