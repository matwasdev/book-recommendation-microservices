package com.example.book_service.book.service;

import com.example.book_service.book.api.dto.book.BookDto;
import com.example.book_service.book.api.dto.request.book.BookCreateRequest;
import com.example.book_service.book.api.dto.request.book.BookUpdateRequest;
import com.example.book_service.book.api.mappers.BookMapper;
import com.example.book_service.book.domain.Author;
import com.example.book_service.book.domain.Book;
import com.example.book_service.book.infrastructure.AuthorRepository;
import com.example.book_service.book.infrastructure.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookDto createBook(BookCreateRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(author);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return bookMapper.toDto(book);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDto(books);
    }



}
