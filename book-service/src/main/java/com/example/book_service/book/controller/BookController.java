package com.example.book_service.book.controller;

import com.example.book_service.book.api.dto.book.BookDto;
import com.example.book_service.book.api.dto.request.book.BookCreateRequest;
import com.example.book_service.book.api.dto.request.book.BookUpdateRequest;
import com.example.book_service.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        log.info("Attempting to create book {}", bookCreateRequest.getTitle());
        BookDto bookDto = bookService.createBook(bookCreateRequest);
        if (bookDto == null) {
            log.warn("Failed to create book {}", bookCreateRequest.getTitle());
            return ResponseEntity.badRequest().build();
        }
        log.info("Book {} successfully created", bookCreateRequest.getTitle());
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BookDto>> getAll() {
        log.info("Attempting to get all books");
        List<BookDto> bookDtos = bookService.getAllBooks();
        if (bookDtos == null || bookDtos.isEmpty()) {
            log.warn("Failed to get all books");
            return ResponseEntity.noContent().build();
        }
        log.info("All {} books successfully retrieved", bookDtos.size());
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        log.info("Attempting to get book by id {}", id);
        BookDto bookDto = bookService.getBookById(id);
        if (bookDto == null) {
            log.warn("Failed to get book by id {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Book {} successfully retrieved", id);
        return ResponseEntity.ok(bookDto);
    }
}
