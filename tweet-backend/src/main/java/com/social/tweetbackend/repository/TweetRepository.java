package com.social.tweetbackend.repository;

import com.social.tweetbackend.model.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    /* GET TWEETS BY USERID */
    @Query(value = "SELECT t FROM Tweet t WHERE t.userId = :userId ORDER BY t.createdAt DESC")
    List<Tweet> getTweetsByUserId(@Param("userId") Long userId);

    /* GET TWEETS BY DATES IN DESC */
    @Query(value = "SELECT t FROM Tweet t ORDER BY t.createdAt DESC")
    List<Tweet> getTweetsByDatesDESC();

}
