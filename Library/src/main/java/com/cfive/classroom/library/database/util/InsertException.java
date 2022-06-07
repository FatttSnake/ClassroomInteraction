package com.cfive.classroom.library.database.util;

public class InsertException extends Exception {
    public InsertException() {
        super("Could not insert the data into database");
    }
}
