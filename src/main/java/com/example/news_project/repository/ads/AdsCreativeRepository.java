package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsCreative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdsCreativeRepository extends JpaRepository<AdsCreative, UUID> {
}
