// NewsResponseDTO.java
package com.example.news_project.dto.news;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsResponseDTO {
    private UUID id;
    private String status;
    private boolean isFeatured;
    private boolean isDeleted;
    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<NewsTranslationDTO> translations;
}
