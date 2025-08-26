package com.example.news_project.controller.ads;

import com.example.news_project.dto.ads.AdsCreativeDto;
import com.example.news_project.service.ads.AdsCreativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/ads/creatives")
@RequiredArgsConstructor
public class AdsCreativeController {

    private final AdsCreativeService creativeService;

    @GetMapping
    public List<AdsCreativeDto> getAll() {
        return creativeService.getAllCreatives();
    }

    @GetMapping("/{id}")
    public AdsCreativeDto getOne(@PathVariable UUID id) {
        return creativeService.getCreativeById(id);
    }

    @PostMapping
    public AdsCreativeDto create(@RequestBody AdsCreativeDto dto) {
        return creativeService.createCreative(dto);
    }

    @PutMapping("/{id}")
    public AdsCreativeDto update(@PathVariable UUID id, @RequestBody AdsCreativeDto dto) {
        return creativeService.updateCreative(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        creativeService.deleteCreative(id);
    }
}
