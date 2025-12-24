package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(Integer day, Integer month, Integer year) {
        super(String.format(Messages.getProperty("exception.invalidDate.message"), day, month, year));
    }
}
