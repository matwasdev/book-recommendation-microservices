package com.example.review_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class BookClient {

    private final RestTemplate restTemplate;

    @Value("${book-service.url}")
    private String bookServiceUrl;

    public BookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isBookValid(Long bookId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    bookServiceUrl + "/api/books/" + bookId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            System.out.println("Response body: " + response.getBody());

            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}