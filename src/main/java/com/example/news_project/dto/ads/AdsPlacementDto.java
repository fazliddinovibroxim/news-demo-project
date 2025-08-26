package com.example.news_project.dto.ads;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdsPlacementDto {
    private UUID id;
    private String code;
    private String title;
    private String description;
    private boolean isActive;
}
