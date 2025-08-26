package com.example.news_project.service.ads;

import com.example.news_project.dto.ads.AdsCampaignDto;
import com.example.news_project.entity.ads.AdsCampaign;
import com.example.news_project.enums.NewsStatus;
import com.example.news_project.repository.ads.AdsCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdsCampaignService {
    private final AdsCampaignRepository repository;


    public AdsCampaign publish(UUID id) {
        AdsCampaign c = getCampaign(id);
        if (c.getStatus() == NewsStatus.REVIEW || c.getStatus() == NewsStatus.DRAFT) {
            c.setStatus(NewsStatus.PUBLISHED);
            return repository.save(c);
        }
        throw new IllegalStateException("Invalid status flow");
    }
    public AdsCampaign archive(UUID id) {
        AdsCampaign c = getCampaign(id);
        c.setStatus(NewsStatus.ARCHIVED);
        return repository.save(c);
    }

        @Transactional
        public void applySchedule() {
            LocalDateTime now = LocalDateTime.now();
            List<AdsCampaign> all = repository.findAll();

            for (AdsCampaign c : all) {
                if (c.getPublishAt() != null && c.getPublishAt().isBefore(now) && c.getStatus() != NewsStatus.PUBLISHED) {
                    c.setStatus(NewsStatus.PUBLISHED);
                }
                if (c.getUnpublishAt() != null && c.getUnpublishAt().isBefore(now) && c.getStatus() == NewsStatus.PUBLISHED) {
                    c.setStatus(NewsStatus.UNPUBLISHED);
                }
            }
            repository.saveAll(all);
        }

    public AdsCampaign unpublish(UUID id) {
        AdsCampaign c = getCampaign(id);
        if (c.getStatus() == NewsStatus.PUBLISHED) {
            c.setStatus(NewsStatus.UNPUBLISHED);
            return repository.save(c);
        }
        throw new IllegalStateException("Invalid flow status");
    }


    public List<AdsCampaignDto> getAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    public AdsCampaign getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
    }

    public AdsCampaign create(AdsCampaignDto dto) {
        AdsCampaign campaign = mapToEntity(dto);
        campaign.setStatus(NewsStatus.valueOf(dto.getStatus().name()));
        AdsCampaign saved = repository.save(campaign);
        return saved;
    }

    public AdsCampaign update(UUID id, AdsCampaignDto dto) {
        AdsCampaign campaign = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        campaign.setName(dto.getName());
        campaign.setAdvertiser(dto.getAdvertiser());
        campaign.setStatus(NewsStatus.valueOf(dto.getStatus().name()));
        campaign.setStartAt(dto.getStartAt());
        campaign.setEndAt(dto.getEndAt());
        campaign.setDailyCapImpressions(dto.getDailyCapImpressions());
        campaign.setDailyCapClicks(dto.getDailyCapClicks());

        AdsCampaign updated = repository.save(campaign);
        return updated;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private AdsCampaignDto mapToDto(AdsCampaign campaign) {
        return AdsCampaignDto.builder()
                .id(campaign.getId())
                .name(campaign.getName())
                .advertiser(campaign.getAdvertiser())
                .status(campaign.getStatus())
                .startAt(campaign.getStartAt())
                .endAt(campaign.getEndAt())
                .dailyCapImpressions(campaign.getDailyCapImpressions())
                .dailyCapClicks(campaign.getDailyCapClicks())
                .createdAt(campaign.getCreatedAt())
                .build();
    }

    private AdsCampaign mapToEntity(AdsCampaignDto dto) {
        AdsCampaign campaign = new AdsCampaign();
        campaign.setName(dto.getName());
        campaign.setAdvertiser(dto.getAdvertiser());
        campaign.setStatus(NewsStatus.valueOf(dto.getStatus().name()));
        campaign.setStartAt(dto.getStartAt());
        campaign.setEndAt(dto.getEndAt());
        campaign.setDailyCapImpressions(dto.getDailyCapImpressions());
        campaign.setDailyCapClicks(dto.getDailyCapClicks());
        return campaign;
    }

    private AdsCampaign getCampaign(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
    }

    public void softDelete(UUID id) {
        AdsCampaign c = getCampaign(id);
        c.setDeleted(true);
        repository.save(c);
    }

    public void restore(UUID id) {
        AdsCampaign c = getCampaign(id);
        c.setDeleted(false);
        repository.save(c);
    }
    public void hardDelete(UUID id) {
        repository.deleteById(id);
    }
}
