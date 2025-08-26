package com.example.news_project.dto.category;

import lombok.Data;

import java.util.UUID;

@Data
public class TagResponse {
    private UUID id;
    private String code;
    private boolean isActive;
}
