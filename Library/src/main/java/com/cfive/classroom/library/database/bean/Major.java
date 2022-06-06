package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 专业类，用于专业信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Major {
    private final int majorID;
    private String majorName;
    private Faculty faculty;

    /**
     * 构造专业对象
     *
     * @param majorID 专业编号，用于识别唯一专业
     * @param majorName 专业名
     * @param faculty 隶属院系
     */
    public Major(@NotNull int majorID, @NotNull String majorName, @NotNull Faculty faculty) {
        this.majorID = majorID;
        this.majorName = majorName;
        this.faculty = faculty;
    }

    /**
     * 获取专业编号，用于识别唯一专业
     *
     * @return <code>int</code> 专业编号
     */
    public int getMajorID() {
        return majorID;
    }

    /**
     * 获取专业名
     *
     * @return <code>String</code> 专业名
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * 设置专业名
     *
     * @param majorName 专业名
     */
    public void setMajorName(@NotNull String majorName) {
        this.majorName = majorName;
    }

    /**
     * 获取隶属院系
     *
     * @return <code>Faculty</code> 隶属院系
     * @see Faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * 设置隶属院系
     *
     * @param faculty 隶属院系
     * @see Faculty
     */
    public void setFaculty(@NotNull Faculty faculty) {
        this.faculty = faculty;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Major{" +
                "majorID=" + majorID +
                ", majorName='" + majorName + '\'' +
                ", faculty=" + faculty +
                '}';
    }
}
