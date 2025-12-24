package com.mine.manager.exception;

import com.mine.manager.util.Messages;

import java.time.LocalDate;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, Integer id) {
        super(String.format(Messages.getProperty("exception.entityNotFoundById.message"), entity, id.toString()));
    }
    public EntityNotFoundException(String entity, String invalidValue) {
        super(String.format(Messages.getProperty("exception.entityNotFoundByValue.message"), entity, invalidValue));
    }
    public EntityNotFoundException(String entity, String property, String invalidValue) {
        super(String.format(Messages.getProperty("exception.entityNotFoundByPropertyAndValue.message"), entity, property, invalidValue));
    }
    public EntityNotFoundException(String entity, String property1, String invalidValue1, String property2, String invalidValue2) {
        super(String.format(Messages.getProperty("exception.entityNotFoundBy2PropertiesAndValues.message"), entity, property1, invalidValue1, property2, invalidValue2));
    }
    public EntityNotFoundException(String entity, String column, String invalidValue, LocalDate date) {
        super(String.format(Messages.getProperty("exception.entityNotFoundByColumnAndDate.message"), entity, column, invalidValue, date));
    }
}
