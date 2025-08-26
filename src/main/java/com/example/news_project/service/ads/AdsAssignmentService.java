package com.example.news_project.service.ads;

import com.example.news_project.dto.ads.AdsAssignmentDto;
import com.example.news_project.entity.ads.*;
import com.example.news_project.repository.ads.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdsAssignmentService {

    private final AdsAssignmentRepository assignmentRepo;
    private final AdsPlacementRepository placementRepo;
    private final AdsCampaignRepository campaignRepo;
    private final AdsCreativeRepository creativeRepo;

    public List<AdsAssignment> getAll() {
        return assignmentRepo.findAll();
    }

    public AdsAssignment getById(String id) {
        return assignmentRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public AdsAssignment create(AdsAssignmentDto dto) {
        AdsAssignment assignment = new AdsAssignment();
        assignment.setPlacement(placementRepo.findById(dto.getPlacementId()).orElseThrow());
        assignment.setCampaign(campaignRepo.findById(dto.getCampaignId()).orElseThrow());
        assignment.setCreative(creativeRepo.findById(dto.getCreativeId()).orElseThrow());
        assignment.setWeight(dto.getWeight());
        assignment.setLangFilter(dto.getLangFilter());
        assignment.setCategoryFilter(dto.getCategoryFilter());
        assignment.setStartAt(dto.getStartAt());
        assignment.setEndAt(dto.getEndAt());
        assignment.setIsActive(dto.getIsActive());
        return assignmentRepo.save(assignment);
    }

    public AdsAssignment update(String id, AdsAssignmentDto dto) {
        AdsAssignment assignment = getById(id);
        assignment.setWeight(dto.getWeight());
        assignment.setLangFilter(dto.getLangFilter());
        assignment.setCategoryFilter(dto.getCategoryFilter());
        assignment.setStartAt(dto.getStartAt());
        assignment.setEndAt(dto.getEndAt());
        assignment.setIsActive(dto.getIsActive());
        return assignmentRepo.save(assignment);
    }

    public void delete(String id) {
        assignmentRepo.deleteById(UUID.fromString(id));
    }
}
