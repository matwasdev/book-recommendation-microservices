package com.example.book_service.book.api.mappers;

import com.example.book_service.book.api.dto.author.AuthorDto;
import com.example.book_service.book.domain.Author;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorDto toDto(Author author);
    List<AuthorDto> toDto(List<Author> authors);
    Author toEntity(AuthorDto authorDto);

}
