package com.example.review_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendRecommendationMessage(Long bookId, int rating,String content ,String authUsername) {
        String message = String.format("{\"bookId\":\"%d\", \"rating\":\"%d\", \"content\":\"%s\" ,\"username\":\"%s\"}", bookId, rating, content, authUsername);
        kafkaTemplate.send("recommendations-topic", message);
    }
}
