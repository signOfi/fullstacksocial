package com.social.tweetbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.*;

public record TweetDto(
        long tweetId,
        long userId,

        @Size(max = 144, message = "Tweet cannot exceed 144 characters")
        String text,

        @Size(max = 50, message = "Hashtags cannot exceed 50 characters")
        String hashTags,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        int likeCount,
        List<Long> replyIds,
        Long parentTweetId,
        List<LikeDto> likes,
        String username
) {
}