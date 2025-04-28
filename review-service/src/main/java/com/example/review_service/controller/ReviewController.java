package com.example.review_service.controller;

import com.example.review_service.api.dto.request.ReviewCreateRequest;
import com.example.review_service.api.dto.review.ReviewDto;
import com.example.review_service.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid ReviewCreateRequest request, HttpServletRequest httpServletRequest) {
        log.info("Attempting to create review");
        ReviewDto reviewDto = reviewService.createReview(request, httpServletRequest);
        if (reviewDto == null) {
            log.warn("Failed to create review");
            return ResponseEntity.badRequest().build();
        }
        log.info("Successfully created review");
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        log.info("Attempting to get all reviews");
        List<ReviewDto> reviewDtos = reviewService.getAllReviews();
        if (reviewDtos == null || reviewDtos.isEmpty()) {
            log.warn("Failed to get all reviews");
            return ResponseEntity.badRequest().build();
        }
        log.info("Successfully retrieved all reviews");
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        log.info("Attempting to get review with id: {} ", id);
        ReviewDto reviewDto = reviewService.getReviewById(id);
        if (reviewDto == null) {
            log.warn("Failed to get review with id: {}", id);
            return ResponseEntity.badRequest().build();
        }
        log.info("Successfully retrieved review with id: {}", id);
        return ResponseEntity.ok(reviewDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto> deleteReview(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        log.info("Attempting to delete review with id: {} ", id);
        reviewService.deleteReviewById(id, httpServletRequest);
        log.info("Successfully deleted review with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
