package com.example.news_project.service.news;

import com.example.news_project.entity.AuthUser;
import com.example.news_project.entity.news.News;
import com.example.news_project.entity.news.NewsHistory;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.AuthUserRepository;
import com.example.news_project.repository.news.NewsHistoryRepository;
import com.example.news_project.repository.news.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsHistoryService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    NewsHistoryRepository newsHistoryRepository;

    public News changeStatus(String newsId, NewsStatus newStatus, AuthUser user) {
        News news = newsRepository.findById(UUID.fromString(newsId)).orElseThrow();
        if (authUserRepository.findById(user.getId()).isPresent() && user.getRole().equals("ADMIN")) {
            NewsHistory history = new NewsHistory();
            history.setNews(news);
            history.setFromStatus(news.getStatus());
            history.setToStatus(newStatus);
            history.setDiffJson("{}");
            history.setChangedAt(Timestamp.from(Instant.now()));
            newsHistoryRepository.save(history);

            news.setStatus(newStatus);
            newsRepository.save(news);
            return news;
        }
        return null;
    }}