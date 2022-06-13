package com.cfive.classroom.library.database.bean;

import java.time.LocalDateTime;

/**
 * 考勤类，用于考勤信息封装
 *
 * @author FatttSnake
 * @version 1.0
 */
public class Attendance {
    private final String attID;
    private Course course;
    private Student student;
    private LocalDateTime attTime;
    private AttStatus attStatus;

    /**
     * 构造考勤对象
     *
     * @param attID 考勤编号，用于识别唯一考勤
     * @param course 隶属课程
     * @param student 隶属学生
     * @param attTime 考勤时间
     * @param attStatus 考勤状态
     */
    public Attendance(String attID, Course course, Student student, LocalDateTime attTime, AttStatus attStatus) {
        this.attID = attID;
        this.course = course;
        this.student = student;
        this.attTime = attTime;
        this.attStatus = attStatus;
    }

    /**
     * 获取考勤编号
     *
     * @return <code>String</code> 考勤编号，UUID字符串
     */
    public String getAttID() {
        return attID;
    }

    /**
     * 获取隶属课程
     *
     * @return <code>Course</code> 隶属课程
     * @see Course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * 设置隶属课程
     *
     * @param course 隶属课程
     * @see Course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * 获取隶属学生
     *
     * @return <code>Student</code> 隶属学生
     * @see Student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * 设置隶属学生
     *
     * @param student 隶属学生
     * @see Student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * 获取考勤时间
     *
     * @return <code>LocalDateTime</code> 考勤时间
     */
    public LocalDateTime getAttTime() {
        return attTime;
    }

    /**
     * 设置考勤时间
     *
     * @param attTime 考勤时间
     */
    public void setAttTime(LocalDateTime attTime) {
        this.attTime = attTime;
    }

    /**
     * 获取考勤状态
     *
     * @return <code>AttStatus</code> 考勤状态
     * @see AttStatus
     */
    public AttStatus getAttStatus() {
        return attStatus;
    }

    /**
     * 设置考勤状态
     *
     * @param attStatus 考勤状态
     * @see AttStatus
     */
    public void setAttStatus(AttStatus attStatus) {
        this.attStatus = attStatus;
    }

    /**
     * 仅用于测试使用
     *
     * @return <code>String</code> 包含所有属性的字符串
     */
    @Override
    public String toString() {
        return "Attendance{" +
                "attID='" + attID + '\'' +
                ", course=" + course +
                ", student=" + student +
                ", attTime=" + attTime +
                ", attStatus=" + attStatus +
                '}';
    }
}
