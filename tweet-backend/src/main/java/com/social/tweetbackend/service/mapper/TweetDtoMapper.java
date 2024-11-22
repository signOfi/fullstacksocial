package com.social.tweetbackend.service.mapper;

import com.social.tweetbackend.model.dto.TweetDto;
import com.social.tweetbackend.model.entity.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Function;

@Service
public class TweetDtoMapper implements Function<Tweet, TweetDto> {

    private final LikeDtoMapper likeDtoMapper;

    @Autowired
    public TweetDtoMapper(LikeDtoMapper likeDtoMapper) {
        this.likeDtoMapper = likeDtoMapper;
    }

    @Override
    public TweetDto apply(Tweet tweet) {

        return new TweetDto(
                tweet.getTweetId(),
                tweet.getUserId(),
                tweet.getText(),
                tweet.getHashTags(),
                tweet.getCreatedAt(),
                tweet.getLikeCount(),
                getRepliesId(tweet.getReplies()),
                getParentTweetId(tweet.getParentTweet()),
                tweet.getLikesList().stream().map(likeDtoMapper).toList(),
                tweet.getUsername()
        );
    }

    private Long getParentTweetId(Tweet parentTweet) {
        return parentTweet != null ? parentTweet.getTweetId() : null;
    }


    private List<Long> getRepliesId(List<Tweet> tweetList) {
        List<Long> res = new ArrayList<>();
        for (Tweet tweet: tweetList)
            res.add(tweet.getTweetId());
        return res;
    }


}
