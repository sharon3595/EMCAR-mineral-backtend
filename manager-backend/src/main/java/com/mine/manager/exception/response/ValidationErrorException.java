package com.mine.manager.exception.response;

public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException(String messages) {
        super(String.format("%s", messages));
    }
}