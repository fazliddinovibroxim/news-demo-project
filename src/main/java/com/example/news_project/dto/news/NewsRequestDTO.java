// NewsRequestDTO.java
package com.example.news_project.dto.news;

import com.example.news_project.entity.Attachment;
import com.example.news_project.entity.category_teg.Tag;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsRequestDTO {
    private UUID authorId;
    private UUID categoryId;
    private List<Attachment> attachmentList;
    private boolean isFeatured;
    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;
    private Set<Tag> tags;
//    private List<NewsTranslationDTO> translations;
    Map<String, NewsTranslationDto> translations;
}
