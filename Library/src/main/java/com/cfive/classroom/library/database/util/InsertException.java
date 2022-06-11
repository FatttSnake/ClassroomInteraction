package com.cfive.classroom.library.database.util;

/**
 * 插入异常
 *
 * @author FatttSnake
 * @version 1.0
 */
public class InsertException extends Exception {
    public InsertException() {
        super("Could not insert the data into database");
    }
}
