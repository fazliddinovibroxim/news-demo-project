package com.example.news_project.dto.ads;

import com.example.news_project.entity.AuthUser;
import com.example.news_project.enums.NewsStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdsCampaignDto {

    private UUID id;
    private String name;
    private NewsStatus status;
    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;
    private boolean isDeleted;
    private AuthUser advertiser;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long dailyCapImpressions;
    private Long dailyCapClicks;
    private LocalDateTime createdAt;
    private List<CampaignTranslationDto> translations;
}
