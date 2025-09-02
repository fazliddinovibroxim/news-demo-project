package com.example.news_project.service.news;

import com.example.news_project.dto.news.*;
import com.example.news_project.entity.news.News;
import com.example.news_project.entity.news.NewsTranslation;
import com.example.news_project.enums.NewsLanguages;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.news.NewsRepository;
import com.example.news_project.repository.news.NewsTranslationRepository;
import com.example.news_project.service.AttachmentService;
import com.example.news_project.util.SlugUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final NewsTranslationRepository translationRepository;
    private AttachmentService attachmentService;

    private static final Logger log = LoggerFactory.getLogger(NewsService.class);

    public Page<NewsDto> getAllNews(
            String status,
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

        Specification<News> spec = Specification.allOf();
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (authorId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("author").get("id"), authorId));
        }
        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
        }
        if (tag != null) {
            spec = spec.and((root, query, cb) -> cb.isMember(tag, root.get("tags")));
        }
        if (lang != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lang"), lang));
        }
        if (from != null && to != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("createdAt"), from, to));
        }

        Page<News> newsPage = newsRepository.findAll(spec, pageable);

        return newsPage.map(this::mapToDto);
    }

    public Page<News> getAll(int page, int size) {
        if (size > pageSize)
            size = pageSize;
        Pageable pageable = PageRequest.of(page, size);
        Page<News> all = newsRepository.findAll(pageable);
        return all;
    }


    public NewsTranslationDto getNewsByLang(String  newsId, NewsLanguages lang) {
        NewsTranslation translation = translationRepository
                .findByNewsIdAndLang(newsId, lang)
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        return NewsTranslationDto.builder()
                .lang(translation.getLang())
                .title(translation.getTitle())
                .slug(translation.getSlug())
                .summary(translation.getSummary())
                .content(translation.getContent())
                .metaTitle(translation.getMetaTitle())
                .metaDescription(translation.getMetaDescription())
                .build();
    }


    @Cacheable(value = "news",key = "#id")
    public News getNewsById(String id) {
        log.info("❌ Cache MISS → fetching from DB, id={}", id);
        return newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.warn("News not found with id={}", id); // warning
                    return new RuntimeException();
    });
}

    public NewsDto createNews(NewsRequestDTO dto) {

        log.info("Creating news with title: {}", dto.getNewsTitle());
        if (dto.getPublishAt() != null && dto.getUnpublishAt() != null) {
            if (!dto.getPublishAt().isBefore(dto.getUnpublishAt())) {
                throw new IllegalArgumentException("publishAt must be before unPublish_At");
            }
        }
        log.debug("PublishAt: {}, UnpublishAt: {}", dto.getPublishAt(), dto.getUnpublishAt());
        News news = new News();
        news.setAuthorId(String.valueOf(dto.getAuthorId()));
        news.setCategoryId(String.valueOf(dto.getCategoryId()));
        news.setTags(dto.getTags());
        news.setStatus(NewsStatus.PUBLISHED);
        news.setCreatedAt(LocalDateTime.now());
        news.setDeleted(false);
        news.setAttachments(dto.getAttachmentList());

        List<NewsTranslation> translations = new ArrayList<>();

        dto.getTranslations().forEach((lang, tDto) -> {
            NewsTranslation translation = NewsTranslation.builder()
                    .news(news)
                    .lang(NewsLanguages.valueOf(lang))
                    .title(tDto.getTitle())
                    .slug(tDto.getSlug())
                    .summary(tDto.getSummary())
                    .content(tDto.getContent())
                    .metaTitle(tDto.getMetaTitle())
                    .metaDescription(tDto.getMetaDescription())
                    .build();

            // generate slug
            String slug = SlugUtil.toSlug(tDto.getTitle());
            translation.setSlug(slug);

            translation.setNews(news);
            translations.add(translation);

    });

        news.setTranslations(translations);

        newsRepository.save(news);
        return mapToDto(news);
    }

    @CachePut(value = "news", key = "#id")
    public NewsDto updateNews(String id, NewsUpdateDto dto) {
//        if (dto.getPublishAt() != null && dto.getUnpublishAt() != null) {
//            if (!dto.getPublishAt().isBefore(dto.getUnpublishAt())) {
//                throw new IllegalArgumentException("publishAt must be before unpublishAt");
//            }
//        }
        log.info("♻ Cache UPDATED for id={}\"",id);
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
    @CachePut(value = "news", key = "#id")
    public NewsDto updateStatus(String  id, StatusUpdateDto dto) {
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("News not found"));
        news.setStatus(NewsStatus.valueOf(dto.getToStatus()));
        return mapToDto(newsRepository.save(news));
    }

    @CacheEvict(value = "news", key = "#id")
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

    @CacheEvict(value = "news", key = "#id")
    public String  hardDeleteNews(String id) {
        log.info("\uD83D\uDDD1 Cache EVICTED for id={}",id);
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
                news.getCategoryId() != null ? String.valueOf(news.getCategoryId()) : null,
                news.getAuthorId() != null ? NewsStatus.valueOf(news.getAuthorId()) : null,
                news.getTranslations().stream().map(tr -> new NewsTranslationDto(
                        tr.getLang(), tr.getTitle(), tr.getSlug(),tr.getSummary(),tr.getContent(),tr.getMetaTitle(),tr.getMetaDescription()
                )).toList()
        );
    }
}
