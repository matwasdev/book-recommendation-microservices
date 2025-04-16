package com.example.recommendation_service.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String content;

    private Long bookId;

    private Integer rating;

    @Column(name = "recommended_at")
    private ZonedDateTime recommendedAt;


    @PrePersist
    protected void onCreate() {
        recommendedAt = ZonedDateTime.now();
    }

}
