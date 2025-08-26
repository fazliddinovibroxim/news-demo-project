package com.example.news_project.controller.ads;

import com.example.news_project.dto.ads.AdsAssignmentDto;
import com.example.news_project.entity.ads.AdsAssignment;
import com.example.news_project.service.ads.AdsAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/ads/assignments")
@RequiredArgsConstructor
public class AdsAssignmentController {

    private final AdsAssignmentService service;

    @GetMapping
    public ResponseEntity<List<AdsAssignment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdsAssignment> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AdsAssignment> create(@RequestBody AdsAssignmentDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdsAssignment> update(@PathVariable String id, @RequestBody AdsAssignmentDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
