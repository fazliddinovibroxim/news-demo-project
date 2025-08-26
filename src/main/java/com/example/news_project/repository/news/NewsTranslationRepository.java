package com.example.news_project.repository.news;

import com.example.news_project.entity.news.NewsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsTranslationRepository extends JpaRepository<NewsTranslation, UUID> {
    List<NewsTranslation> findByNewsId(Long newsId);
}
