package com.cfive.classroom.library.database.util;

/**
 * 依赖项不存在异常
 *
 * @author FatttSnake
 * @version 1.0
 */
public class DependenciesNotFoundException extends Exception {
    public DependenciesNotFoundException() {
        super("Could not found dependencies");
    }
}
