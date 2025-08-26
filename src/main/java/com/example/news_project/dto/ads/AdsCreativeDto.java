package com.example.news_project.dto.ads;

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
public class AdsCreativeDto {
    private UUID id;
    private UUID campaignId;
    private String type;
    private String landingUrl;
    private UUID imageMediaId;
    private String htmlSnippet;
    private boolean isActive;
    private LocalDateTime createdAt;
    private List<AdsCreativeTranslationDto> translations;
}
