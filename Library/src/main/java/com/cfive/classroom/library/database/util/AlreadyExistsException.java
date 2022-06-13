package com.cfive.classroom.library.database.util;

/**
 * 项目已存在异常
 *
 * @author FatttSnake
 * @version 1.0
 */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
        super("This item already exists");
    }
}
