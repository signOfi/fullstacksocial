package com.social.tweetbackend.service.mapper;

import com.social.tweetbackend.model.dto.LikeDto;
import com.social.tweetbackend.model.entity.Like;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LikeDtoMapper implements Function<Like, LikeDto> {
    @Override
    public LikeDto apply(Like like) {
        return new LikeDto(
                like.getLikeId(),
                like.getUserId(),
                like.getTweet().getTweetId()
        );
    }


}
