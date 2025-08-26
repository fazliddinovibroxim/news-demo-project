package com.example.news_project.controller;
import com.example.news_project.entity.news.News;
import com.example.news_project.service.PublicService;
import com.example.news_project.service.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    @Autowired
    NewsService newsService;

    @Autowired
    PublicService publicService;

    @GetMapping()
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "30", required = false) int size

    ) {
        return ResponseEntity.ok(newsService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        News one = newsService.getNewsById(id);
        return ResponseEntity.status(one == null ? 404 : 200).body(one);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<News> getNewsBySlug(
            @PathVariable String slug,
            @RequestParam(defaultValue = "en") String lang) {

        News news = publicService.getNewsBySlug(slug, lang);
        return ResponseEntity.ok(news);
    }

    //  yana davomi bor edi , voqtim yetmadi.

}