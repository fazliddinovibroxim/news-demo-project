package com.example.news_project.dto.news;

import com.example.news_project.entity.Attachment;
import com.example.news_project.entity.category_teg.Tag;
import com.example.news_project.enums.NewsStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
    @NotNull
    private String id;
    @NotNull
    private String authorId;
    @NotNull
    private String categoryId;
    private List<Attachment> attachments;


    @NotNull(message = "status is required")
    private NewsStatus status = NewsStatus.DRAFT;

    @NotBlank
    private String newsTitle;
    private boolean isFeatured;
    private boolean isDeleted;
    private Set<Tag> tags = new HashSet<>();
    @FutureOrPresent(message = "Publish date has to be now or in the future")
    private LocalDateTime publishAt;
    @Future
    private LocalDateTime unpublishAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private LocalDateTime deletedAt;
    private List<NewsTranslationDto> translations;

    public NewsDto(String id, NewsStatus authorId, String categoryId, NewsStatus status, List<NewsTranslationDto> translations) {
        this.id = id;
        this.authorId = String.valueOf(authorId);
        this.categoryId = categoryId;
        this.status = status;
        this.translations = translations;
    }
}
