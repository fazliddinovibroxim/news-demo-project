package com.example.news_project.dto.auth_user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateProfileDto {
    @NotBlank
    @Size(min = 4, max = 25)
    private String username;
    @NotBlank
    @Size(min = 3, max = 100)
    private String fullName;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}