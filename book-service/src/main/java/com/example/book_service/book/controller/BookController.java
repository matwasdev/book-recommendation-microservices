package com.example.book_service.book.controller;

import com.example.book_service.book.api.dto.book.BookDto;
import com.example.book_service.book.api.dto.request.book.BookCreateRequest;
import com.example.book_service.book.api.dto.request.book.BookUpdateRequest;
import com.example.book_service.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;


    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        BookDto bookDto = bookService.createBook(bookCreateRequest);
        if(bookDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookDto);
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<BookDto>> getAll() {
        List<BookDto> bookDtos = bookService.getAllBooks();
        if(bookDtos==null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        if(bookDto==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bookDto);
    }



}
