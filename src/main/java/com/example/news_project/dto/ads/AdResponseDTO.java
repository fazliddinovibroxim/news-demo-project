package com.example.news_project.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdResponseDTO {
    private String  placementId;
    private String  campaignId;
    private String  creativeId;
    private String lang;
    private String category;
    private String creativeUrl;
}
