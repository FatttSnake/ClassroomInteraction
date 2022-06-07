package com.cfive.classroom.library.database.util;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
        super("This item already exists");
    }
}
