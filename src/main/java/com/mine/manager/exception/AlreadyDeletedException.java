package com.mine.manager.exception;

import com.mine.manager.util.Messages;

public class AlreadyDeletedException extends RuntimeException {

  public AlreadyDeletedException(String entity, Integer id) {
    super(String.format(Messages.getProperty("exception.alreadyDeleted.message"), entity, id));
  }
}
