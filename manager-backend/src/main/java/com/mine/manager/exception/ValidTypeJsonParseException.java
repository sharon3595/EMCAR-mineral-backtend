package com.mine.manager.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ValidTypeJsonParseException extends JsonProcessingException {

    public ValidTypeJsonParseException(String msg, JsonLocation loc, Throwable rootCause) {
        super(msg, loc, rootCause);
    }
}
