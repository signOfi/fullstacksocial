package com.social.tweetbackend.exception;

public class InvalidCredentialsExceptionJwt extends JWTAuthenticationException {
    public InvalidCredentialsExceptionJwt(String username) {
        super(String.format("Invalid credentials for user: '%s'", username), "username", username);
    }
}
