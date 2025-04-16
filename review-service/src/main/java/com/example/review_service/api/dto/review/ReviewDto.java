package com.example.review_service.api.dto.review;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long bookId;
    private String reviewerUsername;
    private String content;
    private int rating;
}