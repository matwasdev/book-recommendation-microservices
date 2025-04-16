package com.example.book_service.book.api.dto.request.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorUpdateRequest {

    @NotNull
    private Long id;

    @Size(max = 40)
    private String name;

    @Size(max = 40)
    private String surname;

}