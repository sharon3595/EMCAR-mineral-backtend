package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class HasAsociatedEntityException extends RuntimeException {

    public HasAsociatedEntityException(String entity, String asociatedEntity) {
        super(String.format(Messages.getProperty("exception.hasAssociatedEntity.message"), entity, asociatedEntity));
    }
}
