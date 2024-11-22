package com.social.tweetbackend.exception;

public class InvalidEntityRelationException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Long fieldValue;
    private String fieldValueStr;

    public InvalidEntityRelationException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public InvalidEntityRelationException(String resourceName, String fieldName, String fieldValueStr) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValueStr));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValueStr = fieldValueStr;
    }
}
