package com.social.tweetbackend.security.repository;

import com.social.tweetbackend.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = "SELECT u.username FROM Users u WHERE u.id = :id")
    String getUsernameById(@Param("id") Long id);
}
