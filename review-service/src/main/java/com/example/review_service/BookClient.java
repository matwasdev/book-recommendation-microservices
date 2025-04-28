package com.example.review_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class BookClient {

    private final RestTemplate restTemplate;

    @Value("${book-service.url}")
    private String bookServiceUrl;

    public BookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isBookValid(Long bookId) {
        log.info("Checking if book with id: {} is valid", bookId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(bookServiceUrl + "/api/books/" + bookId, HttpMethod.GET, entity, String.class);

            log.info("Response: {}", response.getBody());
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            log.warn("HttpClientErrorException occurred", e);
            return false;
        }
    }
}