package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AdsCampaignRepository extends JpaRepository<AdsCampaign, UUID> {
    // For public view
    @Query("""
        select c from AdsCampaign c
        where c.status = 'PUBLISHED'
          and c.publishAt <= :now
          and (c.unpublishAt is null or c.unpublishAt > :now)
          and c.isDeleted = false
    """)
    List<AdsCampaign> findAllPublic(@Param("now") LocalDateTime now);
}
