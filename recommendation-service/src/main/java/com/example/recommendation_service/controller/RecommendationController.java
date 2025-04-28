package com.example.recommendation_service.controller;


import com.example.recommendation_service.api.dto.RecommendationDto;
import com.example.recommendation_service.domain.Recommendation;
import com.example.recommendation_service.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recommendations")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDto> getRecommendationById(@PathVariable Long id) {
        log.info("Attempting to get recommendation by id: {}", id);
        RecommendationDto recommendationDto = recommendationService.getRecommendationById(id);
        if (recommendationDto == null) {
            log.warn("Failed to get recommendation with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Recommendation with id {} successfully retrieved", id);
        return ResponseEntity.ok(recommendationDto);
    }

    @GetMapping("/latest")
    public ResponseEntity<RecommendationDto> getLatestRecommendation() {
        log.info("Attempting to get latest recommendation");
        RecommendationDto recommendationDto = recommendationService.getLatestRecommendation();
        if (recommendationDto == null) {
            log.warn("Failed to get latest recommendation");
            return ResponseEntity.notFound().build();
        }
        log.info("Latest recommendation successfully retrieved");
        return ResponseEntity.ok(recommendationDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RecommendationDto>> getAllRecommendations() {
        log.info("Attempting to get all recommendations");
        List<RecommendationDto> recommendationDto = recommendationService.getAllRecommendations();
        if (recommendationDto == null || recommendationDto.isEmpty()) {
            log.warn("Failed to get all recommendations");
            return ResponseEntity.notFound().build();
        }
        log.info("All recommendations successfully retrieved");
        return ResponseEntity.ok(recommendationDto);
    }
}
