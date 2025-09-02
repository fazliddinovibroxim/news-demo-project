package com.example.news_project.entity.news;

import com.example.news_project.entity.Attachment;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.entity.category_teg.Category;
import com.example.news_project.entity.category_teg.Tag;
import com.example.news_project.enums.NewsStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Audited
@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;
    @JoinColumn(name = "author_id", nullable = false)
    private String  authorId;
    @JoinColumn(name = "category_id")
    private String  categoryId;

    @Column(nullable = false)
    private String newsTitle;

    @JoinColumn(name = "cover_media_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    // Enum for status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsStatus status = NewsStatus.DRAFT;

    @ManyToMany
    @JoinTable(
            name = "news_tag",
            schema = "news_schema",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Column(nullable = false)
    private Set<Tag> tags = new HashSet<>();

    private boolean isFeatured = false;
    private boolean isDeleted = false;

    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser createdBy;

    @JoinColumn(name = "updated_by_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser updatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<NewsTranslation> translations = new ArrayList<>();
}
