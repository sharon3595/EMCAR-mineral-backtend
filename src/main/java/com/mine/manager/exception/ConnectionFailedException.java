package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class ConnectionFailedException extends RuntimeException {
    public ConnectionFailedException(String systemName, String errorMessage) {
        super(String.format(Messages.getProperty("exception.connectionFailed.message"), systemName, errorMessage));
    }
}
