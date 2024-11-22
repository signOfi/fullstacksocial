package com.social.tweetbackend.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResponseDto (
        @NotBlank
        @Size(min=2, max = 50)
        String firstName,

        @NotBlank
        @Size(min=2, max = 50)
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min=2, max = 50)
        String username,

        @NotBlank
        String phoneNumber
) {

}
