package com.example.review_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendRecommendationMessage(Long bookId, int rating, String content, String authUsername) {
        String message = String.format("{\"bookId\":\"%d\", \"rating\":\"%d\", \"content\":\"%s\" ,\"username\":\"%s\"}", bookId, rating, content, authUsername);
        log.info("Review successfully sent to the topic \"recommendations-topic\"");
        kafkaTemplate.send("recommendations-topic", message);
    }
}
