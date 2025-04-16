package com.example.book_service.book.service;

import com.example.book_service.book.api.dto.author.AuthorDto;
import com.example.book_service.book.api.dto.request.author.AuthorCreateRequest;
import com.example.book_service.book.api.dto.request.author.AuthorUpdateRequest;
import com.example.book_service.book.api.mappers.AuthorMapper;
import com.example.book_service.book.domain.Author;
import com.example.book_service.book.infrastructure.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorDto createAuthor(AuthorCreateRequest request) {
        Author author = new Author();
        if(request.getName() != null)
            author.setName(request.getName());

        if(request.getSurname() != null)
            author.setSurname(request.getSurname());

        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authorMapper.toDto(authors);
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        System.out.println(author.getSurname() + " " + author.getName());
        return authorMapper.toDto(author);
    }




}
