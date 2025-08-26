package com.example.news_project.service;

import com.example.news_project.entity.news.News;
import com.example.news_project.repository.category.CategoryRepository;
import com.example.news_project.repository.category.TagRepository;
import com.example.news_project.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public News getNewsBySlug(String slug, String lang) {
        News news = newsRepository.findBySlugAndLang(slug, lang)
                .orElseThrow(() -> new RuntimeException("News not found"));

        return news;
    }

//    public List<CategoryResponse> getCategories(String lang) {
//        return categoryRepository.findAllByIsActiveTrue()
//                .stream()
//                .map(cat -> new CategoryResponse(
//                        cat.getId(),
//                        cat.getName(lang) // multilingual name
//                ))
//                .toList();
//    }
//
//    public List<TagResponse> getTags() {
//        return tagRepository.findAllByIsActiveTrue()
//                .stream()
//                .map(tag -> new TagResponse(
//                        tag.getId(),
//                        tag.getCode()
//                ))
//                .toList();
//    }

}


