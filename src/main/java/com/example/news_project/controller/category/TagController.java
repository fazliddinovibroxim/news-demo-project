package com.example.news_project.controller.category;

import com.example.news_project.dto.category.TagRequest;
import com.example.news_project.dto.category.TagResponse;
import com.example.news_project.service.category.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagResponse> getAll() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public TagResponse getById(@PathVariable UUID id) {
        return tagService.getTag(id);
    }

    @PostMapping
    public TagResponse create(@RequestBody TagRequest request) {
        return tagService.createTag(request);
    }

    @PatchMapping("/{id}")
    public TagResponse update(@PathVariable UUID id, @RequestBody TagRequest request) {
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        tagService.deleteTag(id);
    }
}
