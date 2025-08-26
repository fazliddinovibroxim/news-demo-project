package com.example.news_project.service.news;

import com.example.news_project.dto.news.*;
import com.example.news_project.entity.news.News;
import com.example.news_project.entity.news.NewsTranslation;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.news.NewsRepository;
import com.example.news_project.repository.news.NewsTranslationRepository;
import com.example.news_project.service.AttachmentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final NewsRepository newsRepository;
    @Value("${springdoc.default-pagesize}")
    private Integer pageSize;

    private AttachmentService attachmentService;

    public Page<NewsDto> getAllNews(String status,
                                    UUID authorId,
                                    UUID categoryId,
                                    String tag,
                                    String lang,
                                    LocalDateTime from,
                                    LocalDateTime to,
                                    int page,
                                    int size,
                                    String sort) {

        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        Page<News> newsPage = newsRepository.findAll(pageable);
        return newsPage.map(this::mapToDto);
    }


    public Page<News> getAll(int page, int size) {
        if (size > pageSize)
            size = pageSize;
        Pageable pageable = PageRequest.of(page, size);
        Page<News> all = newsRepository.findAll(pageable);
        return all;
    }
    public News getNewsById(String id) {
        return newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
    }

    public NewsDto createNews(NewsRequestDTO dto) {
        News news = new News();
        news.setAuthorId(dto.getAuthorId());
        news.setCategoryId(dto.getCategoryId());
        news.setTags(dto.getTags());
        news.setStatus(NewsStatus.PUBLISHED);
        news.setCreatedAt(LocalDateTime.now());
        news.setDeleted(false);
        news.setAttachments(dto.getAttachmentList());

        dto.getTranslations().forEach(tr -> {
            NewsTranslation translation = new NewsTranslation();
            translation.setNews(news);
            translation.setLang(tr.getLang());
            translation.setTitle(tr.getTitle());
            translation.setContent(tr.getContent());
            news.getTranslations().add(translation);
        });
        newsRepository.save(news);
        return mapToDto(news);
    }

    public NewsDto updateNews(String id, NewsUpdateDto dto) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        news.setTags(dto.getTags());
        news.getTranslations().clear();
        for (var tr : dto.getTranslations()) {
            NewsTranslation translation = new NewsTranslation();
            translation.setNews(news);
            translation.setLang(tr.getLang());
            translation.setTitle(tr.getTitle());
            translation.setContent(tr.getContent());
            news.getTranslations().add(translation);
        }
        return mapToDto(newsRepository.save(news));
    }

    public NewsDto partialUpdateNews(String id, NewsUpdateDto dto) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        return mapToDto(newsRepository.save(news));
    }

    public NewsDto updateStatus(String  id, StatusUpdateDto dto) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        news.setStatus(NewsStatus.valueOf(dto.getToStatus()));
        return mapToDto(newsRepository.save(news));
    }

    public void softDeleteNews(String id) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        news.setDeleted(true);
        newsRepository.save(news);
    }

    public News restoreNews(String id) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        news.setDeleted(false);
       return newsRepository.save(news);
    }

    public String  hardDeleteNews(String id) {
        newsRepository.deleteById(UUID.fromString(id));
        return "news successfully deleted";
    }

    public List<News> getNewsHistory(String id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = auditReader.getRevisions(News.class, id);
        return revisions.stream()
                .map(rev -> auditReader.find(News.class, id, rev))
                .toList();
    }

    private NewsDto mapToDto(News news) {
        return new NewsDto(
                news.getId(),
                news.getStatus(),
                news.getCategoryId() != null ? news.getCategoryId() : null,
                news.getAuthorId() != null ? news.getAuthorId() : null,
                news.getTranslations().stream().map(tr -> new NewsTranslationDTO(
                        tr.getLang(), tr.getTitle(), tr.getContent()
                )).toList()
        );
    }
}
