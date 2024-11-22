package com.social.tweetbackend.exception;

public class InvalidTokenExceptionJwt extends JWTAuthenticationException{

    public InvalidTokenExceptionJwt(String username) {
        super("User already exists with username", "username", username);
    }

}
