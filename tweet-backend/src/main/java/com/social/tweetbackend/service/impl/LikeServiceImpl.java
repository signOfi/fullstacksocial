package com.social.tweetbackend.service.impl;

import com.social.tweetbackend.exception.ResourceNotFoundException;
import com.social.tweetbackend.model.entity.Like;
import com.social.tweetbackend.model.entity.Tweet;
import com.social.tweetbackend.repository.LikeRepository;
import com.social.tweetbackend.repository.TweetRepository;
import com.social.tweetbackend.security.model.Users;
import com.social.tweetbackend.security.repository.UserRepository;
import com.social.tweetbackend.service.LikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void toggleLike(Long userId, Long tweetId) {

        /* Get tweets */
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow( () -> new ResourceNotFoundException("tweet id", "id", tweetId));

        /* Check if user is valid */
        Users user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("user id", "id", userId) );

        /* Check if the user has liked */
        Like like = likeRepository.findByUserIdAndTweetId(userId, tweetId);

        /* Case I: Already liked */
        if (like != null) {
            tweet.setLikeCount(tweet.getLikeCount() - 1);
            likeRepository.deleteById(like.getLikeId());
        }

        /* Case II: Not liked yet */
        else {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
            Like newLike = new Like();
            newLike.setTweet(tweet);
            newLike.setUserId(userId);
            likeRepository.save(newLike);
        }

        /* Persist the like changes */
        tweetRepository.save(tweet);
    }

    @Override
    public boolean likeStatus(Long userId, Long tweetId) {

        /* Get tweets */
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow( () -> new ResourceNotFoundException("tweet id", "id", tweetId));

        /* Check if user is valid */
        Users user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("user id", "id", userId) );

        /* See if user liked or not */
        Like like = likeRepository.findByUserIdAndTweetId(userId, tweetId);

        return like != null;
    }


}
