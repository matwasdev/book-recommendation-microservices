package com.example.auth_service.user.controller;

import com.example.auth_service.user.api.dto.request.user.UserCreateRequest;
import com.example.auth_service.user.api.dto.user.UserDto;
import com.example.auth_service.user.domain.LoginRequest;
import com.example.auth_service.user.service.AuthService;
import com.example.auth_service.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getUsername());

        if (authService.isUserValid(loginRequest.getUsername(), loginRequest.getPassword())) {
            log.info("User {} successfully logged in", loginRequest.getUsername());

            String token = authService.generateToken(loginRequest.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        log.warn("Invalid Login attempt for user: {}", loginRequest.getUsername());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyMap());
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest request) {
        log.info("Attempting to create user with username: {}", request.getUsername());
        UserDto userDto = userService.createUser(request);
        if (userDto == null) {
            log.warn("Failed to create user with username: {}", request.getUsername());
            return ResponseEntity.notFound().build();
        }
        log.info("User with username: {} successfully created", request.getUsername());
        return ResponseEntity.ok(userDto);
    }
}
