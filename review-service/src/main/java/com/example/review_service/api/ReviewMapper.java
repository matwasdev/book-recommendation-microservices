package com.example.review_service.api;

import com.example.review_service.api.dto.review.ReviewDto;
import com.example.review_service.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewDto toDto(Review review);

    List<ReviewDto> toDto(List<Review> reviews);

    Review toEntity(ReviewDto reviewDto);
}
