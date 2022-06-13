package com.cfive.classroom.library.database.bean;

/**
 * 性别枚举类
 *
 * @author FatttSnake
 * @version 1.0
 */
public enum Gender {
    m("男"), f("女");

    private final String string;

    Gender(String string) {
        this.string = string;
    }

    /**
     * 转换成中文
     *
     * @return <code>String</code> 中文
     */
    @Override
    public String toString() {
        return string;
    }
}
