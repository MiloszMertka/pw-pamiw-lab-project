package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotBlank @Size(max = 255) String username,
        @NotBlank @Size(max = 255) String password
) {

}
