package com.example.book_service.book.controller;

import com.example.book_service.book.api.dto.author.AuthorDto;
import com.example.book_service.book.api.dto.request.author.AuthorCreateRequest;
import com.example.book_service.book.api.dto.request.author.AuthorUpdateRequest;
import com.example.book_service.book.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        AuthorDto authorDto = authorService.createAuthor(request);
        if(authorDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        if(authorDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authorDtos = authorService.getAllAuthors();
        if(authorDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorDtos);
    }





}
