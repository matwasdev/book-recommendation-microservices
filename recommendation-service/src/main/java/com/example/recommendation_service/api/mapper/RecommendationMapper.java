package com.example.recommendation_service.api.mapper;

import com.example.recommendation_service.api.dto.RecommendationDto;
import com.example.recommendation_service.domain.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {

    RecommendationDto toDto(Recommendation recommendation);
    List<RecommendationDto> toDto(List<Recommendation> recommendations);
    Recommendation toEntity(RecommendationDto recommendationDto);

}

