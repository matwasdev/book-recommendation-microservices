package com.example.recommendation_service.controller;


import com.example.recommendation_service.api.dto.RecommendationDto;
import com.example.recommendation_service.domain.Recommendation;
import com.example.recommendation_service.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;



    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDto> getRecommendationById(@PathVariable Long id) {
        RecommendationDto recommendationDto = recommendationService.getRecommendationById(id);
        if(recommendationDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recommendationDto);
    }

    @GetMapping("/latest")
    public ResponseEntity<RecommendationDto> getLatestRecommendations() {
        RecommendationDto recommendationDto = recommendationService.getLatestRecommendation();
        if(recommendationDto==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recommendationDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RecommendationDto>> getAllRecommendations() {
        List<RecommendationDto> recommendationDto = recommendationService.getAllRecommendations();
        if(recommendationDto==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recommendationDto);
    }



}
