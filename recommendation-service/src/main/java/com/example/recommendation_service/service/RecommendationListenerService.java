package com.example.recommendation_service.service;

import com.example.recommendation_service.domain.Recommendation;
import com.example.recommendation_service.domain.ReviewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationListenerService {

    private final ObjectMapper objectMapper;
    private final RecommendationService recommendationService;


    @KafkaListener(topics = "recommendations-topic", groupId = "recommendation-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        try {
            ReviewMessage reviewMessage = objectMapper.readValue(message, ReviewMessage.class);

            System.out.println(reviewMessage.getBookId());
            System.out.println(reviewMessage.getUsername());
            System.out.println(reviewMessage.getContent());
            System.out.println(reviewMessage.getRating());

            if(reviewMessage.getRating() <= 3)
                return;

            recommendationService.createRecommendation(reviewMessage);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}
