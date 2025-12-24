package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class RequestFailedException extends RuntimeException {

    public RequestFailedException(String systemName, String errorMessage) {
        super(String.format(Messages.getProperty("exception.requestFailed.message"), systemName,
                errorMessage));
    }
}

