package com.example.news_project.dto.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryResponse {
    private UUID id;
    private String name;
    private boolean isActive;
    private UUID parentId;
}
