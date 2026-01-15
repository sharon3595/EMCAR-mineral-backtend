package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
    public InvalidValueException(String columnName, String invalidValue) {
        super(String.format(Messages.getProperty("exception.invalidValue.message"), invalidValue, columnName));
    }
}