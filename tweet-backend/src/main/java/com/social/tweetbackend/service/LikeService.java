package com.social.tweetbackend.service;

public interface LikeService {
    void toggleLike(Long userId, Long tweetId);
    public boolean likeStatus(Long userId, Long tweetId);
}
