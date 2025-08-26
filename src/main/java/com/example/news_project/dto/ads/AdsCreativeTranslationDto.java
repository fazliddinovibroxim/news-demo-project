package com.example.news_project.dto.ads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdsCreativeTranslationDto {
    private String lang;
    private String title;
    private String altText;
}
