package com.example.news_project.service;

import com.example.news_project.entity.news.News;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
    @RequiredArgsConstructor
    public class SchedulerService {

        private final NewsRepository newsRepository;

        @Scheduled(fixedRate = 60000, cron = "0 * * * * *")
        public void checkPublishUnpublish() {
            LocalDateTime now = LocalDateTime.now();
            List<News> newsList = newsRepository.findAllToUnpublish(now);

            newsList.forEach(news -> {
                news.setStatus(NewsStatus.PUBLISHED);
                newsRepository.save(news);
            });

            newsRepository.publishScheduledNews(Timestamp.from(Instant.now()));

        }
}
