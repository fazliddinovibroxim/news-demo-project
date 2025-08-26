package com.example.news_project.dto.news;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsTranslationDTO {
    private String lang;
    private String title;
    private String content;
}
