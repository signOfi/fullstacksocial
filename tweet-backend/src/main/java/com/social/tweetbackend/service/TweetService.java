package com.social.tweetbackend.service;

import com.social.tweetbackend.model.dto.TweetDto;
import com.social.tweetbackend.model.entity.Tweet;

import java.util.List;

public interface TweetService {
        List<TweetDto> getAllTweetsByDateDESC();
        TweetDto createTweet(TweetDto tweet);
        List<TweetDto> getTweetsByUserId(Long userId);
        TweetDto updateTweet(Long tweetId, String text, String hashTags);
        void deleteTweet(Long tweetId);
        TweetDto replyToTweet(Long tweetId, TweetDto reply);
}
