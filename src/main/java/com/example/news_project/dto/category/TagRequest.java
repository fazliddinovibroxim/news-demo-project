package com.example.news_project.dto.category;

import lombok.Data;

@Data
public class TagRequest {
    private String code;
    private Boolean isActive;
}
