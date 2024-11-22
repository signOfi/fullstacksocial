package com.social.tweetbackend.service.impl;

import com.social.tweetbackend.exception.ResourceNotFoundException;
import com.social.tweetbackend.model.dto.LikeDto;
import com.social.tweetbackend.model.dto.TweetDto;
import com.social.tweetbackend.model.entity.Like;
import com.social.tweetbackend.model.entity.Tweet;
import com.social.tweetbackend.repository.TweetRepository;
import com.social.tweetbackend.service.TweetService;
import com.social.tweetbackend.service.mapper.TweetDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetDtoMapper tweetDtoMapper;

    public TweetServiceImpl(TweetRepository tweetRepository, TweetDtoMapper tweetDtoMapper) {
        this.tweetRepository = tweetRepository;
        this.tweetDtoMapper = tweetDtoMapper;
    }

    @Override
    public List<TweetDto> getAllTweetsByDateDESC() {
        List<Tweet> tweets = tweetRepository.getTweetsByDatesDESC();
        return tweets.stream()
                .map(tweetDtoMapper)
                .toList();
    }

    @Override
    public TweetDto createTweet(TweetDto tweetDto) {
        Tweet tweet = new Tweet();
        tweet.setUserId(tweetDto.userId());
        tweet.setLikeCount(0);
        tweet.setHashTags(tweetDto.hashTags());
        tweet.setText(tweetDto.text());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setParentTweet(tweet.getParentTweet());
        tweet.setUsername(tweetDto.username());
        tweetRepository.save(tweet);
        return tweetDtoMapper.apply(tweet);
    }

    @Override
    public List<TweetDto> getTweetsByUserId(Long userId) {
        List<Tweet> tweets = tweetRepository.getTweetsByUserId(userId);
        return tweets.stream()
                .map(tweetDtoMapper)
                .toList();
    }

    @Override
    public TweetDto updateTweet(Long tweetId, String text, String hashTags) {
        Tweet tweet = getTweet(tweetId);
        tweet.setText(text);
        tweet.setHashTags(hashTags);
        tweetRepository.save(tweet);
        return tweetDtoMapper.apply(tweet);
    }

    @Override
    public void deleteTweet(Long tweetId) {
        Tweet tweet = getTweet(tweetId);
        tweetRepository.delete(tweet);
    }

    @Override
    public TweetDto replyToTweet(Long tweetId, TweetDto childTweetDto) {

        /* Get parent and child tweets */
        Tweet parentTweet = getTweet(tweetId);
        Tweet childTweet = getTweet(childTweetDto.tweetId());

        /* Set relationships */
        childTweet.setParentTweet(parentTweet);
        parentTweet.getReplies().add(childTweet);

        /* Save to DB */
        tweetRepository.save(parentTweet);
        tweetRepository.save(childTweet);

        return tweetDtoMapper.apply(parentTweet);
    }

    private Tweet getTweet(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("tweet id", "id", tweetId));
    }

}
