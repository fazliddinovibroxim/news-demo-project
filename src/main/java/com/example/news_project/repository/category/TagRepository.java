package com.example.news_project.repository.category;

import com.example.news_project.entity.category_teg.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByCode(String code);
    boolean existsByCode(String code);

    List<Tag> findAllByIsActiveTrue();
}
