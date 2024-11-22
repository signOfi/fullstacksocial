package com.social.tweetbackend.security.model;

public record AuthenticationResponse(
        String token,
        UserResponseDto userResponseDto,
        Long userId
) {
}
