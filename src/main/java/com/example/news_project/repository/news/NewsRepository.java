package com.example.news_project.repository.news;

import com.example.news_project.entity.news.News;
import com.example.news_project.enums.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    List<News> findByIsDeletedFalse();

        @Query("SELECT n FROM News n WHERE n.status = 'PUBLISHED' " +
                "AND n.unpublishAt < :now AND n.isDeleted = false")
        List<News> findAllToUnpublish(@Param("now") LocalDateTime now);

    Optional<News> findBySlugAndLang(String slug, String lang);


    Iterable<Object> findAllToPublish(LocalDateTime now);

    Page<News> searchByLangAndKeyword(String lang, String search, Pageable pageable);

    @Modifying
    @Query("UPDATE News n SET n.status = 'PUBLISHED' WHERE n.status = 'DRAFT' AND n.publishAt <= :now")
    void publishScheduledNews(@Param("now") Timestamp now);

    @Modifying
    @Query("UPDATE News n SET n.status = 'UNPUBLISHED' WHERE n.status = 'PUBLISHED' AND n.unpublishAt <= :now")
    void unpublishScheduledNews(@Param("now") Timestamp now);

    void deleteAllByStatusAndUpdatedAtBefore(NewsStatus archived, Timestamp threshold);
}

