package com.example.news_project.repository.news;

import com.example.news_project.entity.news.NewsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsHistoryRepository extends JpaRepository<NewsHistory, UUID> {
}
