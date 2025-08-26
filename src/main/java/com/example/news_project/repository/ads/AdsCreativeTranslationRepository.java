package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsCreativeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdsCreativeTranslationRepository extends JpaRepository<AdsCreativeTranslation, UUID> {
}
