package com.example.book_service.book.controller;

import com.example.book_service.book.api.dto.author.AuthorDto;
import com.example.book_service.book.api.dto.request.author.AuthorCreateRequest;
import com.example.book_service.book.api.dto.request.author.AuthorUpdateRequest;
import com.example.book_service.book.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        log.info("Attempting to create author: {} {}", request.getName(), request.getSurname());
        AuthorDto authorDto = authorService.createAuthor(request);
        if (authorDto == null) {
            log.warn("Failed to create author: {} {}", request.getName(), request.getSurname());
            return ResponseEntity.notFound().build();
        }
        log.info("Author successfully created: {} {}", authorDto.getName(), authorDto.getSurname());
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        log.info("Attempting to get author by id: {}", id);
        AuthorDto authorDto = authorService.getAuthorById(id);
        if (authorDto == null) {
            log.warn("Failed to get author by id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Author successfully retrieved: {}", authorDto.getName());
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        log.info("Attempting to get all authors");
        List<AuthorDto> authorDtos = authorService.getAllAuthors();
        if (authorDtos == null || authorDtos.isEmpty()) {
            log.warn("Failed to get all authors");
            return ResponseEntity.notFound().build();
        }
        log.info("All authors successfully retrieved: {} authors", authorDtos.size());
        return ResponseEntity.ok(authorDtos);
    }
}
