package com.example.recommendation_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewMessage {

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String content;

    @JsonProperty("bookId")
    private Long BookId;

    @JsonProperty("rating")
    private int rating;

}
