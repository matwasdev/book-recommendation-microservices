package com.example.auth_service.user.api.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 30)
    private String username;

    @Size(max = 100)
    private String password;
}
