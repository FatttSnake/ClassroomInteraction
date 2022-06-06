package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 科目类，用于科目信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Subject {
    private final int subID;
    private String subName;

    /**
     * 构造科目对象
     *
     * @param subID 科目编号，用于识别唯一科目
     * @param subName 科目名
     */
    public Subject(@NotNull int subID, @NotNull String subName) {
        this.subID = subID;
        this.subName = subName;
    }

    /**
     * 获取科目编号，用于识别唯一科目
     *
     * @return <code>int</code> 科目编号
     */
    public int getSubID() {
        return subID;
    }

    /**
     * 获取科目名
     *
     * @return <code>String</code> 科目名
     */
    public String getSubName() {
        return subName;
    }

    /**
     * 设置科目名
     *
     * @param subName 科目名
     */
    public void setSubName(@NotNull String subName) {
        this.subName = subName;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Subject{" +
                "subID=" + subID +
                ", subName='" + subName + '\'' +
                '}';
    }
}
