package com.example.news_project.service;

import com.example.news_project.entity.news.News;
import com.example.news_project.repository.news.NewsRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PublicNewsService {
    private final NewsRepository newsRepository;

    public PublicNewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Cacheable(value = "news_list", key = "'lang=' + #lang + ',page=' + #page + ',size=' + #size + ',search=' + #search")
    public Page<News> getNews(String lang, int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishAt"));
        return newsRepository.searchByLangAndKeyword(lang, search, pageable);
    }

    @Cacheable(value = "news_single", key = "#slug + '_' + #lang")
    public News getNewsBySlug(String slug, String lang) {
        return newsRepository.findBySlugAndLang(slug, lang)
                .orElseThrow(() -> new RuntimeException("News not found"));
    }
}
