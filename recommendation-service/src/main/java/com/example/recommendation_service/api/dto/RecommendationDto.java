package com.example.recommendation_service.api.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDto {

    private String username;

    private Long BookId;

    private String content;

    private Integer rating;

    private ZonedDateTime recommendedAt;
}
