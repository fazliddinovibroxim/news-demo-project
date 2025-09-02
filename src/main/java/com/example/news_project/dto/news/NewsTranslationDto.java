package com.example.news_project.dto.news;

import com.example.news_project.enums.ContentCreativeType;
import com.example.news_project.enums.NewsLanguages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsTranslationDto{
    private NewsLanguages lang = NewsLanguages.UZ;
    @Max(200)
    @Min(3)
    private String title;
    @NotBlank
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug should contain only lowercase letters, numbers, and hyphens")
    private String slug;
    @Min(5000)
    private String summary;
    private ContentCreativeType content = ContentCreativeType.HTML;
    private String metaTitle;
    private String metaDescription;
}