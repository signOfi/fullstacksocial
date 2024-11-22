package com.social.tweetbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tweetId;

    private long userId;

    private String text;

    private String hashTags;

    private LocalDateTime createdAt;

    private int likeCount;

    private String username;

    @OneToMany(mappedBy = "parentTweet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parentTweet")
    private List<Tweet> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_tweet_id")
    @JsonIgnoreProperties("replies")
    private Tweet parentTweet;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tweet")
    private List<Like> likesList = new ArrayList<>();
}
