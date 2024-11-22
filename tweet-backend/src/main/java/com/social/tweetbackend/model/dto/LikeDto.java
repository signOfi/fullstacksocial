package com.social.tweetbackend.model.dto;

public record LikeDto(
        Long likeId,
        Long userId,
        Long tweetId
) {
}