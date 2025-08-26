package com.example.news_project.dto.ads;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampaignTranslationDto {
    private String lang;
    private String title;
    private String content;
}
