package com.social.tweetbackend.repository;

import com.social.tweetbackend.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.userId = :userId AND l.tweet.tweetId = :tweetId")
    Like findByUserIdAndTweetId(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

}
