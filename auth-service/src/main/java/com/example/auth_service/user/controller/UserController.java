package com.example.auth_service.user.controller;

import com.example.auth_service.user.api.dto.request.user.UserCreateRequest;
import com.example.auth_service.user.api.dto.request.user.UserUpdateRequest;
import com.example.auth_service.user.api.dto.user.UserDto;
import com.example.auth_service.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        log.info("Attempting to fetch user with username: {}", username);
        UserDto userDto = userService.getUserByUsername(username);
        if (userDto == null) {
            log.warn("Failed to get user: {}", username);
            return ResponseEntity.notFound().build();
        }
        log.info("User with username: {} found", username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Attempting to fetch all users");
        List<UserDto> userDtos = userService.getAllUsers();
        if (userDtos == null || userDtos.isEmpty()) {
            log.warn("No users found");
            return ResponseEntity.notFound().build();
        }
        log.info("All users successfully retrieved");
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request, HttpServletRequest httpServletRequest) {
        log.info("Attempting to update user with id: {}", id);
        UserDto userDto = userService.updateUser(id, httpServletRequest, request);
        if (userDto == null) {
            log.warn("Failed to update user with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User with id: {} successfully updated", id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        log.info("Attempting to delete user with id: {}", id);
        userService.deleteUser(id, httpServletRequest);
        log.info("User with id: {} successfully deleted", id);
        return ResponseEntity.noContent().build();
    }
}