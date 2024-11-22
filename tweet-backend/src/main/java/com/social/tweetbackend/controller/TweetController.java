package com.social.tweetbackend.controller;

import com.social.tweetbackend.model.dto.TweetDto;
import com.social.tweetbackend.model.entity.Tweet;
import com.social.tweetbackend.service.LikeService;
import com.social.tweetbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final LikeService likeService;

    @Autowired
    public TweetController(TweetService tweetService, LikeService likeService) {
        this.tweetService = tweetService;
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<TweetDto> createTweet(@RequestBody TweetDto tweetDto) {
        tweetDto = tweetService.createTweet(tweetDto);
        return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDto>> getTweetsByDate() {
        List<TweetDto> tweets = tweetService.getAllTweetsByDateDESC();
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TweetDto>> getTweetsByUserId(@PathVariable Long userId) {
        List<TweetDto> tweets = tweetService.getTweetsByUserId(userId);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PatchMapping("/{tweetId}/{text}/{hashTags}")
    public ResponseEntity<TweetDto> updateTweet(
            @PathVariable Long tweetId,
            @PathVariable String text,
            @PathVariable String hashTags
    ) {
        TweetDto tweetDto = tweetService.updateTweet(tweetId, text, hashTags);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<Void> deleteTweet(
            @PathVariable Long tweetId
    ) {
        tweetService.deleteTweet(tweetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reply/{tweetId}")
    public ResponseEntity<TweetDto> replyToTweet (
            @PathVariable Long tweetId,
            @RequestBody TweetDto tweet
    ) {
        TweetDto tweetDto = tweetService.replyToTweet(tweetId, tweet);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @PutMapping("/like/{userId}/{tweetId}")
    public ResponseEntity<Void> toggleLike(
            @PathVariable Long userId,
            @PathVariable Long tweetId) {
        likeService.toggleLike(userId, tweetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/like/{userId}/{tweetId}")
    public ResponseEntity<Boolean> checkLikeStatus(
            @PathVariable Long userId,
            @PathVariable Long tweetId
    ) {
        boolean status = likeService.likeStatus(userId, tweetId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
