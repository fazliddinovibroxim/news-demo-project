package com.example.news_project.repository.news;

import com.example.news_project.entity.news.NewsTranslation;
import com.example.news_project.enums.NewsLanguages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsTranslationRepository extends JpaRepository<NewsTranslation, UUID> {
    List<NewsTranslation> findByNewsId(Long newsId);
    Optional<NewsTranslation> findByNewsIdAndLang(String  newsId, NewsLanguages lang);
}
