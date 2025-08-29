package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AdsAssignmentRepository extends JpaRepository<AdsAssignment, UUID> {
    @Query("""
    SELECT a FROM AdsAssignment a
    WHERE a.placement.isActive = true
      AND a.campaign.status = 'ACTIVE'
      AND a.campaign.startAt <= :now
      AND a.campaign.endAt >= :now
      AND a.creative.isActive = true
      AND (:lang IS NULL OR a.lang = :lang)
      AND (:category IS NULL OR a.category = :category)
""")
    List<AdsAssignment> findActiveAssignments(
            @Param("now") LocalDateTime now,
            @Param("lang") String lang,
            @Param("category") String category
    );

}
