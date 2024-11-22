package com.social.tweetbackend.exception;

public class InvalidFieldException extends JWTAuthenticationException {
    public InvalidFieldException(String message, String fieldName, String fieldValue) {
        super("The field has invalidates the database rules", fieldName, fieldValue);
    }
}
