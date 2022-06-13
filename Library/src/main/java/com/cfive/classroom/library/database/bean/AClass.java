package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 班级类，用于班级信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */

public class AClass {
    private final long classID;
    private Major major;
    private int grade;
    private int classNum;

    /**
     * 构造班级对象
     *
     * @param classID 班级编号，用于识别唯一班级
     * @param major 隶属专业
     * @param grade 年级
     * @param classNum 班号
     */
    public AClass(@NotNull long classID, @NotNull Major major, @NotNull int grade, @NotNull int classNum) {
        this.classID = classID;
        this.major = major;
        this.grade = grade;
        this.classNum = classNum;
    }

    /**
     * 获取班级编号，用于识别唯一班级
     *
     * @return <code>long</code> 班级编号
     */
    public long getClassID() {
        return classID;
    }

    /**
     * 获取隶属专业
     *
     * @return <code>Major</code> 隶属专业
     * @see Major
     */
    public Major getMajor() {
        return major;
    }

    /**
     * 设置隶属专业
     *
     * @param major 隶属专业
     * @see Major
     */
    public void setMajor(@NotNull Major major) {
        this.major = major;
    }

    /**
     * 获取年级
     *
     * @return <code>int</code> 年级
     */
    public int getGrade() {
        return grade;
    }

    /**
     * 设置年级
     *
     * @param grade 年级
     */
    public void setGrade(@NotNull int grade) {
        this.grade = grade;
    }

    /**
     * 获取班号
     *
     * @return <code>int</code> 班号
     */
    public int getClassNum() {
        return classNum;
    }

    /**
     * 设置班号
     *
     * @param classNum 班号
     */
    public void setClassNum(@NotNull int classNum) {
        this.classNum = classNum;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Class{" +
                "classID=" + classID +
                ", major=" + major +
                ", grade=" + grade +
                ", classNum=" + classNum +
                '}';
    }
}
