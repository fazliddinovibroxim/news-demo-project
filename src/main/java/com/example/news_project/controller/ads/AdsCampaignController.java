package com.example.news_project.controller.ads;

import com.example.news_project.dto.ads.AdsCampaignDto;
import com.example.news_project.dto.api_response.ResponseApi;
import com.example.news_project.entity.ads.AdsCampaign;
import com.example.news_project.service.ads.AdsCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/ads/campaigns")
@RequiredArgsConstructor
public class AdsCampaignController {
    private final AdsCampaignService service;

    @GetMapping
    public List<AdsCampaignDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AdsCampaign getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public AdsCampaign create(@RequestBody AdsCampaignDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public AdsCampaign update(@PathVariable UUID id, @RequestBody AdsCampaignDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<?> publish(@PathVariable UUID id) {
        return ResponseEntity.ok(service.publish(id));
    }

    @PatchMapping("/{id}/unpublish")
    public ResponseEntity<?> unpublish(@PathVariable UUID id) {
        return ResponseEntity.ok(service.unpublish(id));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<?> archive(@PathVariable UUID id) {
        return ResponseEntity.ok(service.archive(id));
    }
    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<Void> softDelete(@PathVariable UUID id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable UUID id) {
        service.restore(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id) {
        service.hardDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/schedule/apply")
    public ResponseApi applySchedule() {
        service.applySchedule();
        return new ResponseApi(201,"Campaign schedule applied successfully.");
    }
}
