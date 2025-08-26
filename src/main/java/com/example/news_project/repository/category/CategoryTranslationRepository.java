package com.example.news_project.repository.category;

import com.example.news_project.entity.category_teg.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, UUID> {
}
