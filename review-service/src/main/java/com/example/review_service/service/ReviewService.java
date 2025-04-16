package com.example.review_service.service;

import com.example.review_service.AuthClient;
import com.example.review_service.BookClient;
import com.example.review_service.api.ReviewMapper;
import com.example.review_service.api.dto.request.ReviewCreateRequest;
import com.example.review_service.api.dto.review.ReviewDto;
import com.example.review_service.domain.Review;
import com.example.review_service.infrastructure.ReviewRepository;
import com.example.review_service.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.AuthorizationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final KafkaProducerService kafkaProducerService;
    private final JwtUtil jwtUtil;
    private final ReviewMapper reviewMapper;
    private final BookClient bookClient;
    private final AuthClient authClient;


    public ReviewDto createReview(ReviewCreateRequest request, HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String authenticatedUsername = jwtUtil.extractUsername(jwt);

        if(canPostReview(authenticatedUsername,request.getBookId())){
            Review review = new Review();
            review.setBookId(request.getBookId());
            review.setContent(request.getContent());
            review.setRating(request.getRating());
            review.setReviewerUsername(authenticatedUsername);

            review = reviewRepository.save(review);

            kafkaProducerService.sendRecommendationMessage(request.getBookId(), request.getRating(),request.getContent() ,authenticatedUsername);
            return reviewMapper.toDto(review);
        }

        return null;
    }

    public List<ReviewDto> getAllReviews(){
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.toDto(reviews);
    }

    public ReviewDto getReviewById(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Review not found"));
        return reviewMapper.toDto(review);
    }


    public void deleteReviewById(Long id, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String authenticatedUsername = jwtUtil.extractUsername(jwt);

        Review review = reviewRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Review not found"));

        if(review.getReviewerUsername().equals(authenticatedUsername)){
            reviewRepository.delete(review);
        }
        else
            throw new AuthorizationException("You are not authorized to delete this review");
    }




    private boolean canPostReview(String authenticatedUsername, Long reviewId) {
        boolean isUserValid = authClient.isUserValid(authenticatedUsername);
        boolean isBookValid = bookClient.isBookValid(reviewId);

        return isUserValid && isBookValid;
    }

}
