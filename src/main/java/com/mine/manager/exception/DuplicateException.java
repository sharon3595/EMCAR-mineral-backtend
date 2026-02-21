package com.mine.manager.exception;


import com.mine.manager.util.Messages;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
    public DuplicateException(String entityName, String columnName, String value) {
        super(String.format(Messages.getProperty("exception.duplicate.message"), entityName, columnName, value));
    }
}
