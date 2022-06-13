package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 教师类，用于教师信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Teacher {
    private final long tchID;
    private String tchName;
    private Gender gender;
    private Faculty faculty;
    private String password;
    private String salt;

    /**
     * 构造教师对象
     *
     * @param tchID 工号，用于识别唯一教师
     * @param tchName 教师姓名
     * @param gender 性别
     * @param faculty 隶属院系
     * @param password 密码
     * @param salt 加盐
     */
    public Teacher(@NotNull long tchID, @NotNull String tchName, @NotNull Gender gender, @NotNull Faculty faculty, @NotNull String password, @NotNull String salt) {
        this.tchID = tchID;
        this.tchName = tchName;
        this.gender = gender;
        this.faculty = faculty;
        this.password = password;
        this.salt = salt;
    }

    /**
     * 获取工号，用于识别唯一教师
     *
     * @return <code>long</code> 教师编号
     */
    public long getTchID() {
        return tchID;
    }

    /**
     * 获取教师姓名
     *
     * @return <code>String</code> 教师姓名
     */
    public String getTchName() {
        return tchName;
    }

    /**
     * 设置教师姓名
     * @param tchName 教师姓名
     */
    public void setTchName(@NotNull String tchName) {
        this.tchName = tchName;
    }

    /**
     * 获取性别
     *
     * @return <code>Gender</code> 性别
     * @see Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     * @see Gender
     */
    public void setGender(@NotNull Gender gender) {
        this.gender = gender;
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
     * 获取密码
     *
     * @return <code>String</code> 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    /**
     * 获取加盐
     *
     * @return <code>String</code> 加盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置加盐
     *
     * @param salt 加盐
     */
    public void setSalt(@NotNull String salt) {
        this.salt = salt;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "tchID=" + tchID +
                ", tchName='" + tchName + '\'' +
                ", gender=" + gender +
                ", faculty=" + faculty +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
