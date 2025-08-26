package com.example.news_project.service.ads;

import com.example.news_project.dto.ads.AdsPlacementDto;
import com.example.news_project.entity.ads.AdsPlacement;
import com.example.news_project.repository.ads.AdsPlacementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdsPlacementService {

    private final AdsPlacementRepository repository;

    public AdsPlacementDto create(AdsPlacementDto dto) {
        if (repository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Placement code already exists: " + dto.getCode());
        }
        AdsPlacement entity = mapToEntity(dto);
        return mapToDto(repository.save(entity));
    }

    public AdsPlacementDto getById(UUID id) {
        AdsPlacement placement = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Placement not found"));
        return mapToDto(placement);
    }

    public List<AdsPlacementDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public AdsPlacementDto update(UUID id, AdsPlacementDto dto) {
        AdsPlacement entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Placement not found"));

        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.isActive());

        return mapToDto(repository.save(entity));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Placement not found");
        }
        repository.deleteById(id);
    }

    // === Mapping methods inside service (no separate mapper) ===
    private AdsPlacementDto mapToDto(AdsPlacement entity) {
        return AdsPlacementDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .build();
    }

    private AdsPlacement mapToEntity(AdsPlacementDto dto) {
        return AdsPlacement.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .isActive(dto.isActive())
                .build();
    }
}
