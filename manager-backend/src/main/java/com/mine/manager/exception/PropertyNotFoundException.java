package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class PropertyNotFoundException extends RuntimeException {

  public PropertyNotFoundException(String message) {
    super(message);
  }
  public PropertyNotFoundException(String property,String entity) {
    super(String.format(Messages.getProperty("exception.propertyNotFound.message"), property, entity));
  }
}