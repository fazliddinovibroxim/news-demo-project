package com.example.news_project.entity.ads;

import com.example.news_project.dto.ads.CampaignTranslationDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.enums.AdsStatus;
import com.example.news_project.enums.NewsStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ads_campaign", schema = "ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdsCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // columnga nom bermasa ham variablning nomini oladi, lekin aniqlashtirish uchun name berilgan

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser")
    private AuthUser advertiser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NewsStatus status;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    private boolean isDeleted = false;

    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;

    @Column(name = "daily_cap_impressions")
    private Long dailyCapImpressions;

    @Column(name = "daily_cap_clicks")
    private Long dailyCapClicks;

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
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
