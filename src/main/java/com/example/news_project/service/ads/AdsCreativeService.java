package com.example.news_project.service.ads;

import com.example.news_project.dto.ads.AdsCreativeDto;
import com.example.news_project.dto.ads.AdsCreativeTranslationDto;
import com.example.news_project.entity.ads.AdsCampaign;
import com.example.news_project.entity.ads.AdsCreative;
import com.example.news_project.entity.ads.AdsCreativeTranslation;
import com.example.news_project.enums.AdsStatus;
import com.example.news_project.enums.ContentCreativeType;
import com.example.news_project.repository.ads.AdsCampaignRepository;
import com.example.news_project.repository.ads.AdsCreativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdsCreativeService {

    private final AdsCreativeRepository creativeRepository;
    private final AdsCampaignRepository campaignRepository;

    public List<AdsCreativeDto> getAllCreatives() {
        return creativeRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public AdsCreativeDto getCreativeById(UUID id) {
        AdsCreative creative = creativeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creative not found"));
        return mapToDto(creative);
    }

    public AdsCreativeDto createCreative(AdsCreativeDto dto) {
        AdsCampaign campaign = campaignRepository.findById(dto.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        AdsCreative creative = new AdsCreative();
        creative.setCampaign(campaign);
        creative.setType(ContentCreativeType.valueOf(dto.getType()));
        creative.setLandingUrl(dto.getLandingUrl());
        creative.setImageMediaId(dto.getImageMediaId());
        creative.setHtmlSnippet(dto.getHtmlSnippet());
        creative.setActive(dto.isActive());

        if (dto.getTranslations() != null) {
            List<AdsCreativeTranslation> translations = dto.getTranslations().stream()
                    .map(tr -> {
                        AdsCreativeTranslation translation = new AdsCreativeTranslation();
                        translation.setParentEntity(creative);
                        translation.setLang(tr.getLang());
                        translation.setTitle(tr.getTitle());
                        translation.setAltText(tr.getAltText());
                        return translation;
                    }).toList();
            creative.setTranslations(translations);
        }

        AdsCreative saved = creativeRepository.save(creative);
        return mapToDto(saved);
    }

    public AdsCreativeDto updateCreative(UUID id, AdsCreativeDto dto) {
        AdsCreative creative = creativeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creative not found"));

        if (dto.getCampaignId() != null) {
            AdsCampaign campaign = campaignRepository.findById(dto.getCampaignId())
                    .orElseThrow(() -> new RuntimeException("Campaign not found"));
            creative.setCampaign(campaign);
        }

        creative.setType(dto.getType() != null ? ContentCreativeType.valueOf(dto.getType()) : creative.getType());
        creative.setLandingUrl(dto.getLandingUrl());
        creative.setImageMediaId(dto.getImageMediaId());
        creative.setHtmlSnippet(dto.getHtmlSnippet());
        creative.setActive(dto.isActive());

        // reset translations
        creative.getTranslations().clear();
        if (dto.getTranslations() != null) {
            dto.getTranslations().forEach(tr -> {
                AdsCreativeTranslation translation = new AdsCreativeTranslation();
                translation.setParentEntity(creative);
                translation.setLang(tr.getLang());
                translation.setTitle(tr.getTitle());
                translation.setAltText(tr.getAltText());
                creative.getTranslations().add(translation);
            });
        }

        AdsCreative updated = creativeRepository.save(creative);
        return mapToDto(updated);
    }

    public void deleteCreative(UUID id) {
        creativeRepository.deleteById(id);
    }

    private AdsCreativeDto mapToDto(AdsCreative creative) {
        return AdsCreativeDto.builder()
                .id(creative.getId())
                .campaignId(creative.getCampaign().getId())
                .type(creative.getType().name())
                .landingUrl(creative.getLandingUrl())
                .imageMediaId(creative.getImageMediaId())
                .htmlSnippet(creative.getHtmlSnippet())
                .isActive(creative.isActive())
                .createdAt(creative.getCreatedAt().toLocalDateTime())
                .translations(
                        creative.getTranslations().stream()
                                .map(tr -> new AdsCreativeTranslationDto(tr.getLang(), tr.getTitle(), tr.getAltText()))
                                .toList()
                )
                .build();
    }
}
