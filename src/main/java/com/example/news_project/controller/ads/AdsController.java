package com.example.news_project.controller.ads;

import com.example.news_project.dto.ads.AdResponseDTO;
import com.example.news_project.entity.ads.AdsAssignment;
import com.example.news_project.service.ads.AdsAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsAssignmentService adsService;

    @GetMapping
    public ResponseEntity<AdResponseDTO> getAd(
            @RequestParam UUID placementId,
            @RequestParam(required = false) String lang,
            @RequestParam(required = false) String category
    ) {
        AdsAssignment ad = adsService.getAd(lang, category, placementId);

        if (ad == null) {
            return ResponseEntity.noContent().build();
        }
        AdResponseDTO dto = AdResponseDTO.builder()
                .placementId(String.valueOf(ad.getPlacement().getId()))
                .campaignId(String.valueOf(ad.getCampaign().getId()))
                .creativeId(String.valueOf(ad.getCreative().getId()))
                .lang(ad.getLangFilter())
                .category(ad.getCategoryFilter())
                .creativeUrl(ad.getCreative().getLandingUrl())
                .build();

        return ResponseEntity.ok(dto);
    }
}
