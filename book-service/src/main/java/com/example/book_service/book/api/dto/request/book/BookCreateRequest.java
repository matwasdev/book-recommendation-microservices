package com.example.book_service.book.api.dto.request.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    private Long authorId;
}
