package com.cfive.classroom.library.database.util;

/**
 * 无配置文件异常
 *
 * @author FatttSnake
 * @version 1.0
 */
public class NoConfigException extends Exception {
    public NoConfigException() {
        super("Could not load configuration file");
    }
}
