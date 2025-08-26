package com.example.news_project.service;

import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.AdsRepository;
import com.example.news_project.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DailyScheduler {

    private final NewsRepository newsRepository;
    private final AdsRepository adsRepository;

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupArchivedNews() {

        LocalDateTime threshold = LocalDateTime.now().minusDays(90);

        newsRepository.deleteAllByStatusAndUpdatedAtBefore(NewsStatus.ARCHIVED, Timestamp.valueOf(threshold));
    }

    @Scheduled(cron = "0 30 2 * * *")
    public void syncAdsAssignments() {
        adsRepository.findAll().forEach(ad -> {
            if (ad.getEndAt() != null && ad.getEndAt().isBefore(Timestamp.from(Instant.now()).toLocalDateTime())) {
                ad.setIsActive(false);
                adsRepository.save(ad);
            }
        });
    }
}
