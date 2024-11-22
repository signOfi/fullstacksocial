package com.social.tweetbackend.exception;

public class UserAlreadyExistsExceptionJwt extends JWTAuthenticationException{

    public UserAlreadyExistsExceptionJwt(String username) {
        super(String.format("User already exists with username: '%s'", username), "username", username);
    }

}
