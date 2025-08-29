package com.example.news_project.dto.news;

import com.example.news_project.enums.NewsStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
    private UUID id;
    private UUID authorId;
    private UUID categoryId;
    private UUID coverMediaId;
    private String status;
    private boolean isFeatured;
    private boolean isDeleted;
    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private List<NewsTranslationDto> translations;
    public NewsDto(UUID id,
                   NewsStatus status,
                   UUID categoryId,
                   UUID authorId,
                   List<NewsTranslationDto> translations) {
        this.id = id;
        this.status = status != null ? status.name() : null;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.translations = translations;
    }
}
