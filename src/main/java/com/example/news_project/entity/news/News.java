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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private UUID id;

    @JoinColumn(name = "author_id", nullable = false)
    private UUID authorId;

    @JoinColumn(name = "category_id")
    private UUID categoryId;

    // Optional cover media (image, video, etc.)
//    @ManyToOne(fetch = FetchType.LAZY)
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
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false)
    private boolean isFeatured = false;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "created_by_id")
    private AuthUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @LastModifiedBy
    @JoinColumn(name = "updated_by_id")
    private AuthUser updatedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NewsTranslation> translations = new HashSet<>();
}
