package com.example.auth_service.user.service;

import com.example.auth_service.user.domain.User;
import com.example.auth_service.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user with username: {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username {} not found in database", username);
            return new EntityNotFoundException("Username: " + username + " not found");
        });

        log.debug("Successfully loaded user: {}", username);

        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(new String[]{user.getRole().getName()}).build();
    }
}
