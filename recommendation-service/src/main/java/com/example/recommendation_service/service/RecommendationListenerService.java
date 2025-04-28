package com.example.recommendation_service.service;

import com.example.recommendation_service.domain.Recommendation;
import com.example.recommendation_service.domain.ReviewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationListenerService {

    private final ObjectMapper objectMapper;
    private final RecommendationService recommendationService;


    @KafkaListener(topics = "recommendations-topic", groupId = "recommendation-group")
    public void listen(String message) {
        log.info("Received recommendation message from \"recommendations-topic\": {}", message);

        try {
            ReviewMessage reviewMessage = objectMapper.readValue(message, ReviewMessage.class);

            log.info("Parsed review message - Book ID: {}, Username: {}, Rating: {}, Content: {}", reviewMessage.getBookId(), reviewMessage.getUsername(), reviewMessage.getRating(), reviewMessage.getContent());

            if (reviewMessage.getRating() <= 3) {
                log.info("Recommendation with rating {} lower than 4, skipping save to the database.", reviewMessage.getRating());
                return;
            }

            recommendationService.createRecommendation(reviewMessage);
            log.info("Recommendation successfully saved to the database for Book ID: {} and User: {}", reviewMessage.getBookId(), reviewMessage.getUsername());

        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException while processing the recommendation message", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Unexpected error while processing recommendation message", e);
            throw new RuntimeException("Unexpected error during message processing", e);
        }
    }
}
