package com.social.tweetbackend.security.model;

/* Registration requires all fields */
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
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
        @Size(min=8, max=100)
        String password,

        @NotBlank
        String phoneNumber
) {
}
