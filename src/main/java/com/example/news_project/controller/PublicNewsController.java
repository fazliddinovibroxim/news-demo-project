package com.example.news_project.controller;

import com.example.news_project.entity.news.News;
import com.example.news_project.service.PublicNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/news")
@RequiredArgsConstructor
public class PublicNewsController {

    private final PublicNewsService publicNewsService;

    // GET /public/news?lang=uz&page=0&size=10&search=
    @GetMapping
    public ResponseEntity<Page<News>> getNews(
            @RequestParam(defaultValue = "en") String lang,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        Page<News> newsPage = publicNewsService.getNews(lang, page, size, search);
        return ResponseEntity.ok(newsPage);
    }

    // GET /public/news/{slug}?lang=uz
    @GetMapping("/{slug}")
    public ResponseEntity<News> getNewsBySlug(
            @PathVariable String slug,
            @RequestParam(defaultValue = "en") String lang
    ) {
        News news = publicNewsService.getNewsBySlug(slug, lang);
        return ResponseEntity.ok(news);
    }
}
