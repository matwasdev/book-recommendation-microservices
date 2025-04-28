package com.example.recommendation_service.service;

import com.example.recommendation_service.api.dto.RecommendationDto;
import com.example.recommendation_service.api.mapper.RecommendationMapper;
import com.example.recommendation_service.domain.Recommendation;
import com.example.recommendation_service.domain.ReviewMessage;
import com.example.recommendation_service.infrastructure.RecommendationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;

    public RecommendationDto createRecommendation(ReviewMessage reviewMessage) {
        Recommendation recommendation = new Recommendation();
        recommendation.setUsername(reviewMessage.getUsername());
        recommendation.setContent(reviewMessage.getContent());
        recommendation.setBookId(reviewMessage.getBookId());
        recommendation.setRating(reviewMessage.getRating());

        recommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.toDto(recommendation);
    }

    public List<RecommendationDto> getAllRecommendations() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        return recommendationMapper.toDto(recommendations);
    }

    public RecommendationDto getRecommendationById(Long id) {
        Recommendation recommendation = recommendationRepository.findById(id).orElseThrow(() -> {
            log.warn("Could not find recommendation with id {}", id);
            return new EntityNotFoundException("Recommendation not found");
        });
        return recommendationMapper.toDto(recommendation);
    }

    public RecommendationDto getLatestRecommendation() {
        Recommendation recommendation = recommendationRepository.findTopByOrderByRecommendedAtDesc();
        if (recommendation == null) {
            log.warn("Could not find latest recommendation");
            throw new EntityNotFoundException("Recommendation not found");
        }
        return recommendationMapper.toDto(recommendation);
    }
}
