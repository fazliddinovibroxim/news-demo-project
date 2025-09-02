package com.example.news_project.controller.news;

import com.example.news_project.dto.news.NewsDto;
import com.example.news_project.dto.news.NewsRequestDTO;
import com.example.news_project.dto.news.NewsUpdateDto;
import com.example.news_project.dto.news.StatusUpdateDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.entity.news.News;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.service.news.NewsHistoryService;
import com.example.news_project.service.news.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/admin/news")
@RequiredArgsConstructor
@Slf4j
public class NewsAdminController {

    private final NewsHistoryService newsHistoryService;
    private final NewsService newsService;
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable String id) {
        log.info("Request to get news with id={}", id);
        var news = newsService.getNewsById(id);
        log.debug("Returning news: {}", news);
        return news == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(news);
    }
    @GetMapping
    public Page<NewsDto> getAllNews(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID authorId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String lang,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        return newsService.getAllNews(status, authorId, categoryId, tag, lang, from, to, page, size, sort);
    }


    @PreAuthorize("hasAnyAuthority('ADD_NEWS')")
    @PostMapping
    public ResponseEntity<NewsDto> createNews(@Valid @RequestBody NewsRequestDTO newsDto) {
        var created = newsService.createNews(newsDto);
        return ResponseEntity.status(201).body(created);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_NEWS')")
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable String id,
                                              @Valid @RequestBody NewsUpdateDto newsDto) {
        var updated = newsService.updateNews(id, newsDto);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_NEWS')")
    @PatchMapping("/{id}")
    public ResponseEntity<NewsDto> partialUpdateNews(@PathVariable String id,
                                                     @Valid @RequestBody NewsUpdateDto newsDto) {
        var updated = newsService.partialUpdateNews(id, newsDto);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NewsDto> updateNewsStatus(@PathVariable String id,
                                                    @RequestBody StatusUpdateDto dto) {
        var updated = newsService.updateStatus(id, dto);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }
    @PreAuthorize("hasAnyAuthority('DELETE_NEWS')")
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteNews(@PathVariable String id) {
        var deleted = newsService.hardDeleteNews(id);
        return deleted == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteNews(@PathVariable String id) {
        newsService.softDeleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<News> restoreNews(@PathVariable String id) {
        var restored = newsService.restoreNews(id);
        return restored == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(restored);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<Object> getNewsHistory(@PathVariable String id) {
        var history = newsService.getNewsHistory(id);
        return history == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(history);
    }

    @PatchMapping("/{newsId}/status")
    public ResponseEntity<String> changeStatus(
            @PathVariable String newsId,
            @RequestParam NewsStatus status,
           @AuthenticationPrincipal AuthUser user){
        newsHistoryService.changeStatus(newsId, status, user);
        return ResponseEntity.ok("News status updated successfully!");
    }
}
