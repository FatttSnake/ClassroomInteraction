package com.cfive.classroom.library.database.bean;

import com.sun.istack.internal.NotNull;

/**
 * 院系类，用于院系信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Faculty {
    private final int facID;
    private String facName;

    /**
     * 构造院系对象
     *
     * @param facID 院系编号，用于识别唯一院系
     * @param facName 院系名
     */
    public Faculty(@NotNull int facID, @NotNull String facName) {
        this.facID = facID;
        this.facName = facName;
    }

    /**
     * 获取院系编号，用于识别唯一院系
     *
     * @return 院系编号
     */
    public int getFacID() {
        return facID;
    }

    /**
     * 获取院系名
     *
     * @return 院系名
     */
    public String getFacName() {
        return facName;
    }

    /**
     * 设置院系名
     *
     * @param facName <code>String</code> 院系名
     */
    public void setFacName(@NotNull String facName) {
        this.facName = facName;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Faculty{" +
                "facID=" + facID +
                ", facName='" + facName + '\'' +
                '}';
    }
}
