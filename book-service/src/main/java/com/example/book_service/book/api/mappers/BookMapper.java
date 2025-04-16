package com.example.book_service.book.api.mappers;



import com.example.book_service.book.api.dto.book.BookDto;
import com.example.book_service.book.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(source = "author.id", target = "authorId")
    BookDto toDto(Book book);

    @Mapping(source = "authorId", target = "author.id")
    List<BookDto> toDto(List<Book> books);
    Book toEntity(BookDto booksDto);

}
