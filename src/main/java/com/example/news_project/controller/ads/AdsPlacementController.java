package com.example.news_project.controller.ads;

import com.example.news_project.dto.ads.AdsPlacementDto;
import com.example.news_project.service.ads.AdsPlacementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/ads/placements")
@RequiredArgsConstructor
public class AdsPlacementController {

    private final AdsPlacementService service;

    @PostMapping
    public ResponseEntity<AdsPlacementDto> create(@RequestBody AdsPlacementDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdsPlacementDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AdsPlacementDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdsPlacementDto> update(
            @PathVariable UUID id,
            @RequestBody AdsPlacementDto dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
