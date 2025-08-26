package com.example.news_project.entity.ads;

import com.example.news_project.dto.ads.AdsCreativeTranslationDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.enums.ContentCreativeType;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.IntegerList;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ads_creative", schema = "ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdsCreative {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private AdsCampaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentCreativeType type;

    @Column(name = "landing_url", length = 500)
    private String landingUrl;

    // Optional, depending on creative type
    @Column(name = "image_media_id")
    private UUID imageMediaId;

    @Column(name = "html_snippet", columnDefinition = "TEXT")
    private String htmlSnippet;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private AuthUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @LastModifiedBy
    @JoinColumn(name = "updated_by_id", referencedColumnName = "id")
    private AuthUser updatedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "parentEntity", fetch = FetchType.LAZY)
    private List<AdsCreativeTranslation> translations; // This is the correct use for a collection
}
