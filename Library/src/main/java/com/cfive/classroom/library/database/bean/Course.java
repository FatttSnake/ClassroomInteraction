package com.cfive.classroom.library.database.bean;

import java.time.LocalDateTime;

/**
 * 课程类，用于课程信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Course {
    private final long courID;
    private Subject subject;
    private Teacher teacher;
    private LocalDateTime courTimeFrom;
    private LocalDateTime courTimeEnd;

    /**
     * 构造课程对象
     *
     * @param courID 课程编号，用于识别唯一课程
     * @param subject 隶属科目
     * @param teacher 隶属教师
     * @param courTimeFrom 开始时间
     * @param courTimeEnd 结束时间
     */
    public Course(long courID, Subject subject, Teacher teacher, LocalDateTime courTimeFrom, LocalDateTime courTimeEnd) {
        this.courID = courID;
        this.subject = subject;
        this.teacher = teacher;
        this.courTimeFrom = courTimeFrom;
        this.courTimeEnd = courTimeEnd;
    }

    /**
     * 获取课程编号，用于识别唯一课程
     *
     * @return <code>long</code> 课程编号
     */
    public long getCourID() {
        return courID;
    }

    /**
     * 获取隶属科目
     *
     * @return <code>Subject</code> 隶属科目
     * @see Subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * 设置隶属科目
     *
     * @param subject 隶属科目
     * @see Subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * 获取隶属教师
     *
     * @return <code>Teacher</code> 隶属教师
     * @see Teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * 设置隶属教师
     *
     * @param teacher 隶属教师
     * @see Teacher
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * 获取开始时间
     *
     * @return <code>LocalDateTime</code> 开始时间
     * @see LocalDateTime
     */
    public LocalDateTime getCourTimeFrom() {
        return courTimeFrom;
    }

    /**
     * 设置开始时间
     *
     * @param courTimeFrom 开始时间
     * @see LocalDateTime
     */
    public void setCourTimeFrom(LocalDateTime courTimeFrom) {
        this.courTimeFrom = courTimeFrom;
    }

    /**
     * 获取结束时间
     *
     * @return <code>LocalDateTime</code> 结束时间
     * @see LocalDateTime
     */
    public LocalDateTime getCourTimeEnd() {
        return courTimeEnd;
    }

    /**
     * 设置结束时间
     *
     * @param courTimeEnd 结束时间
     * @see LocalDateTime
     */
    public void setCourTimeEnd(LocalDateTime courTimeEnd) {
        this.courTimeEnd = courTimeEnd;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Course{" +
                "courID=" + courID +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", courTimeFrom=" + courTimeFrom +
                ", courTimeEnd=" + courTimeEnd +
                '}';
    }
}
