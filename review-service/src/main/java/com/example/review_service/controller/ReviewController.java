package com.example.review_service.controller;

import com.example.review_service.api.dto.request.ReviewCreateRequest;
import com.example.review_service.api.dto.review.ReviewDto;
import com.example.review_service.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;



    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid ReviewCreateRequest request, HttpServletRequest httpServletRequest) {
        ReviewDto reviewDto = reviewService.createReview(request, httpServletRequest);
        if(reviewDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviewDtos = reviewService.getAllReviews();
        if(reviewDtos == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.getReviewById(id);
        if(reviewDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reviewDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto> deleteReview(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        reviewService.deleteReviewById(id, httpServletRequest);
        return ResponseEntity.noContent().build();
    }



}
