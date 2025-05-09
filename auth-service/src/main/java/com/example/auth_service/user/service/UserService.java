package com.example.auth_service.user.service;

import com.example.auth_service.user.api.dto.request.user.UserCreateRequest;
import com.example.auth_service.user.api.dto.request.user.UserUpdateRequest;
import com.example.auth_service.user.api.dto.user.UserDto;
import com.example.auth_service.user.api.mappers.UserMapper;
import com.example.auth_service.user.domain.User;
import com.example.auth_service.user.infrastructure.RoleRepository;
import com.example.auth_service.user.infrastructure.UserRepository;
import com.example.auth_service.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserDto createUser(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleRepository.findByName("USER").get());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username {} not found in database", username);
            return new UsernameNotFoundException(username);
        });
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    public UserDto updateUser(Long id, HttpServletRequest httpServletRequest, UserUpdateRequest request) {
        String jwt = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String authenticatedUsername = JwtUtil.extractUsername(jwt);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found in database", id);
            return new EntityNotFoundException("User with id " + id + " not found");
        });

        if (!user.getUsername().equals(authenticatedUsername)) {
            log.warn("Unauthorized update attempt by user: {} for user id: {}", authenticatedUsername, id);
            throw new SecurityException("You are not authorized to update this user");
        }

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id, HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String authenticatedUsername = JwtUtil.extractUsername(jwt);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found in database", id);
            return new EntityNotFoundException("User with id " + id + " not found");
        });

        if (!user.getUsername().equals(authenticatedUsername)) {
            log.warn("Unauthorized update attempt by user: {} for user id: {}", authenticatedUsername, id);
            throw new SecurityException("You are not authorized to delete this user");
        }

        userRepository.deleteById(id);
    }
}
