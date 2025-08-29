package com.example.news_project.dto.news;
import com.example.news_project.entity.category_teg.Tag;
import com.example.news_project.enums.NewsStatus;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsUpdateDto {

    private UUID categoryId;
    private UUID coverMediaId;
    private Boolean isFeatured;
    private NewsStatus status;
    private Date publishAt;
    private Date unpublishAt;
    private Set<Tag> tags;

    private List<NewsTranslationDto> translations;
}
