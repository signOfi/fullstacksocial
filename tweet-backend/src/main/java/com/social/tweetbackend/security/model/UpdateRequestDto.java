package com.social.tweetbackend.security.model;

public record UpdateRequestDto(
        UserResponseDto userResponseDto,
        String sensitiveInformation
) {
}
