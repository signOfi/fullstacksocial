package com.social.tweetbackend.exception;

public class JWTAuthenticationException extends RuntimeException {

    private String fieldName;
    private String fieldValue;

    public JWTAuthenticationException(String message, String fieldName, String fieldValue) {
        super(message);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
