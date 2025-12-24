package com.mine.manager.exception;


import com.mine.manager.util.Messages;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String entityName, String columnName, String value) {
        super(String.format(Messages.getProperty("exception.duplicate.message"), entityName, columnName, value));
    }
    public DuplicateException(String entityName, String column1Name, String value1, String column2Name, String value2) {
        super(String.format(Messages.getProperty("exception.duplicate.2column.combination.message"), entityName, column1Name, value1, column2Name, value2));
    }public DuplicateException(String entityName, String column1Name, String value1, String column2Name, String value2, String column3Name, String value3) {
        super(String.format(Messages.getProperty("exception.duplicate.3column.combination.message"), entityName, column1Name, value1, column2Name, value2, column3Name, value3));
    }
}
