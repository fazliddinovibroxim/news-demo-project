package com.example.news_project.dto.ads;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdsAssignmentDto {
    private UUID id;
    private UUID placementId;
    private UUID campaignId;
    private UUID creativeId;
    private Integer weight;
    private String langFilter;
    private String categoryFilter;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean isActive;
}
