package com.example.news_project.service.category;

import com.example.news_project.dto.category.TagRequest;
import com.example.news_project.dto.category.TagResponse;
import com.example.news_project.entity.category_teg.Tag;
import com.example.news_project.repository.category.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TagResponse getTag(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return mapToResponse(tag);
    }

    public TagResponse createTag(TagRequest request) {
        if (tagRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Tag with this code already exists");
        }
        com.example.news_project.entity.category_teg.Tag tag = new Tag();
        tag.setCode(request.getCode());
        tag.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        return mapToResponse(tagRepository.save(tag));
    }

    public TagResponse updateTag(UUID id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        if (request.getCode() != null) tag.setCode(request.getCode());
        if (request.getIsActive() != null) tag.setIsActive(request.getIsActive());
        return mapToResponse(tagRepository.save(tag));
    }

    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }

    private TagResponse mapToResponse(Tag tag) {
        TagResponse res = new TagResponse();
        res.setId(tag.getId());
        res.setCode(tag.getCode());
        res.setActive(tag.getIsActive());
        return res;
    }
}
