package com.example.news_project.repository.category;
import com.example.news_project.entity.category_teg.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);
    List<Category> findAllByIsActiveTrue();
}
