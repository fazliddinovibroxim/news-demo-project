package com.example.news_project.service.category;
import com.example.news_project.dto.category.CategoryRequest;
import com.example.news_project.dto.category.CategoryResponse;
import com.example.news_project.entity.category_teg.Category;
import com.example.news_project.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoryResponse getCategory(String id) {
        Category category = categoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentId(parent.getId());
        }

        return mapToResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getName() != null) category.setName(request.getName());
        if (request.getIsActive() != null) category.setIsActive(request.getIsActive());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentId(parent.getParentId());
        }

        return mapToResponse(categoryRepository.save(category));
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(UUID.fromString(id));
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse res = new CategoryResponse();
        res.setId(category.getId());
        res.setName(category.getCreatedBy().getFullName());
        res.setActive(category.getIsActive());
        res.setParentId(category.getParentId() != null ? category.getParentId() : null);
        return res;
    }
}
