package com.example.recommendation_service.infrastructure;

import com.example.recommendation_service.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Recommendation findTopByOrderByRecommendedAtDesc();

}
