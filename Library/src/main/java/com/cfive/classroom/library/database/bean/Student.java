package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 学生类，用于学生信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Student {
    private final long stuID;
    private String stuName;
    private Gender gender;
    private Class aClass;
    private String password;
    private String salt;

    /**
     * 构造学生对象
     *
     * @param stuID 学号，用于识别唯一学生
     * @param stuName 学生姓名
     * @param gender 性别
     * @param aClass 隶属班级
     * @param password 密码
     * @param salt 加盐
     */
    public Student(@NotNull long stuID, @NotNull String stuName, @NotNull Gender gender, @NotNull Class aClass, @NotNull String password, @NotNull String salt) {
        this.stuID = stuID;
        this.stuName = stuName;
        this.gender = gender;
        this.aClass = aClass;
        this.password = password;
        this.salt = salt;
    }

    /**
     * 获取学生编号，用于识别唯一学生
     *
     * @return <code>long</code> 学号
     */
    public long getStuID() {
        return stuID;
    }

    /**
     * 获取学生姓名
     *
     * @return <code>String</code> 学生姓名
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 设置学生姓名
     *
     * @param stuName 学生姓名
     */
    public void setStuName(@NotNull String stuName) {
        this.stuName = stuName;
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
     * 获取隶属班级
     *
     * @return <code>Class</code> 隶属班级
     * @see Class
     */
    public Class getaClass() {
        return aClass;
    }

    /**
     * 设置隶属班级
     *
     * @param aClass 隶属班级
     * @see Class
     */
    public void setaClass(@NotNull Class aClass) {
        this.aClass = aClass;
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
        return "Student{" +
                "stuID=" + stuID +
                ", stuName='" + stuName + '\'' +
                ", gender=" + gender +
                ", aClass=" + aClass +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
