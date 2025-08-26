package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsPlacement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdsPlacementRepository extends JpaRepository<AdsPlacement, UUID> {
    boolean existsByCode(String code);
}
