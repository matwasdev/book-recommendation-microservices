package com.example.api_gateway.config;

import com.example.api_gateway.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        return builder.routes()
                .route(r -> r.path("/auth-service/api/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthenticationFilter))
                        .uri("lb://auth-service"))
                .route(r -> r.path("/book-service/api/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthenticationFilter))
                        .uri("lb://book-service"))
                .route(r -> r.path("/review-service/api/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthenticationFilter))
                        .uri("lb://review-service"))
                .route(r -> r.path("/recommendation-service/api/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthenticationFilter))
                        .uri("lb://recommendation-service"))
                .build();
    }
}
