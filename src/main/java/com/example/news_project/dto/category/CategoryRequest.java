package com.example.news_project.dto.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {
    private String name;
    private Boolean isActive;
    private UUID parentId;
}
