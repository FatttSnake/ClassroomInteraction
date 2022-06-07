package com.cfive.classroom.library.database.util;

public class NoConfigException extends Exception {
    public NoConfigException() {
        super("Could not load configuration file");
    }
}
