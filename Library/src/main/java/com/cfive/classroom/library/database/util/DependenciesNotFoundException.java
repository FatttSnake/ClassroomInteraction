package com.cfive.classroom.library.database.util;

public class DependenciesNotFoundException extends Exception {
    public DependenciesNotFoundException() {
        super("Could not found dependencies");
    }
}
