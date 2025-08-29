package com.example.news_project.dto.news;

import com.example.news_project.enums.ContentCreativeType;
import com.example.news_project.enums.NewsLanguages;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsTranslationDto{
    private NewsLanguages lang;
    private String title;
    private String slug;
    private String summary;
    private ContentCreativeType content;
    private String metaTitle;
    private String metaDescription;
}