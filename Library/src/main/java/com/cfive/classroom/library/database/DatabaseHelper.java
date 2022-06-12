package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.operation.*;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.sun.istack.internal.Nullable;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作集合类
 * 所有数据库操作方法全部在这里
 *
 * @author FatttSnake
 * @version 2.1
 */
public class DatabaseHelper {
    /**
     * 查询所有院系
     *
     * @return <code>List</code> 包含 Faculty 类型的数组
     * @see List
     */
    public static List<Faculty> selectAllFromFaculty() throws NoConfigException, SQLException {
        return FacultyOA.selectAll();
    }

    /**
     * 通过院系编号查询院系
     *
     * @param facID 院系编号
     * @return <code>Faculty</code> 院系类
     * @see Faculty
     */
    public static Faculty selectFromFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.select(facID);
    }

    /**
     * 通过院系名查询院系
     *
     * @param facName 院系名
     * @return <code>Faculty</code> 院系类
     * @see Faculty
     */
    public static Faculty selectFromFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.select(facName);
    }

    /**
     * 插入院系
     *
     * @param facName 院系名
     * @return <code>Faculty</code> 院系类
     * @see Faculty
     */
    public static Faculty insertIntoFaculty(String facName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return FacultyOA.insert(facName);
    }

    /**
     * 插入院系（指定院系编号）
     *
     * @param facID 院系编号
     * @param facName 院系名
     * @return <code>Faculty</code> 院系类
     * @see Faculty
     */
    public static Faculty insertIntoFaculty(int facID, String facName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        return FacultyOA.insert(facID, facName);
    }

    /**
     * 通过院系编号查询院系是否存在
     *
     * @param facID 院系编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.isExists(facID);
    }

    /**
     * 通过院系名查询院系是否存在
     *
     * @param facName 院系名
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.isExists(facName);
    }

    /**
     * 通过院系编号删除院系
     *
     * @param facID 院系编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.delete(facID);
    }

    /**
     * 通过院系名删除院系
     *
     * @param facName 院系名
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.delete(facName);
    }

    /**
     * 查询所有科目
     *
     * @return <code>List</code> 包含 Subject 类型的数组
     * @see List
     */
    public static List<Subject> selectAllFromSubject() throws NoConfigException, SQLException {
        return SubjectOA.selectAll();
    }

    /**
     * 通过科目编号查询科目
     *
     * @param subID 科目编号
     * @return <code>Subject</code> 科目类
     * @see Subject
     */
    public static Subject selectFromSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.select(subID);
    }

    /**
     * 通过科目名查询科目
     *
     * @param subName 科目名
     * @return <code>Subject</code> 科目类
     * @see Subject
     */
    public static Subject selectFromSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.select(subName);
    }

    /**
     * 插入科目
     *
     * @param subName 科目名
     * @return <code>Subject</code> 科目类
     * @see Subject
     */
    public static Subject insertIntoSubject(String subName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return SubjectOA.insert(subName);
    }

    /**
     * 插入科目（指定科目编号）
     *
     * @param subID 科目编号
     * @param subName 科目名
     * @return <code>Subject</code> 科目类
     * @see Subject
     */
    public static Subject insertIntoSubject(int subID, String subName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return SubjectOA.insert(subID, subName);
    }

    /**
     * 通过科目编号查询科目是否存在
     *
     * @param subID 科目编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.isExists(subID);
    }

    /**
     * 通过科目名查询科目是否存在
     *
     * @param subName 科目名
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.isExists(subName);
    }

    /**
     * 通过科目编号删除科目
     *
     * @param subID 科目编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.delete(subID);
    }

    /**
     * 通过科目名删除科目
     *
     * @param subName 科目名
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.delete(subName);
    }

    /**
     * 查询所有专业
     *
     * @return <code>List</code> 包含 Major 类型的数组
     * @see List
     */
    public static List<Major> selectAllFromMajor() throws NoConfigException, SQLException {
        return MajorOA.selectAll();
    }

    /**
     * 通过专业编号查询专业
     *
     * @param majorID 专业编号
     * @return <code>Major</code> 专业类
     * @see Major
     */
    public static Major selectFromMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.select(majorID);
    }

    /**
     * 通过专业名查询专业
     *
     * @param majorName 专业名
     * @return <code>Major</code> 专业类
     * @see Major
     */
    public static Major selectFromMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.select(majorName);
    }

    /**
     * 插入专业
     *
     * @param majorName 专业名
     * @param facID 隶属院系编号
     * @return <code>Major</code> 专业类
     * @see Major
     */
    public static Major insertIntoMajor(String majorName, int facID) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return MajorOA.insert(majorName, facID);
    }

    /**
     * 插入专业
     *
     * @param majorName 专业名
     * @param facName 隶属院系名
     * @return <code>Major</code> 专业
     * @see Major
     */
    public static Major insertIntoMajor(String majorName, String facName) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return MajorOA.insert(majorName, facName);
    }

    /**
     * 插入专业（指定专业编号）
     *
     * @param majorID 专业编号
     * @param majorName 专业名
     * @param facID 隶属院系
     * @return <code>Major</code> 专业类
     * @see Major
     */
    public static Major insertIntoMajor(int majorID, String majorName, int facID) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return MajorOA.insert(majorID, majorName, facID);
    }

    /**
     * 插入专业（指定专业编号）
     *
     * @param majorID 专业编号
     * @param majorName 专业名
     * @param facName 隶属院系
     * @return <code>Major</code> 专业类
     * @see Major
     */
    public static Major insertIntoMajor(int majorID, String majorName, String facName) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return MajorOA.insert(majorID, majorName, facName);
    }

    /**
     * 通过专业编号查询专业是否存在
     *
     * @param majorID 专业编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.isExists(majorID);
    }

    /**
     * 通过专业名查询专业是否存在
     *
     * @param majorName 专业名
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.isExists(majorName);
    }

    /**
     * 通过专业编号删除专业
     *
     * @param majorID 专业编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.delete(majorID);
    }

    /**
     * 通过专业名删除专业
     *
     * @param majorName 专业名
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.delete(majorName);
    }

    /**
     * 查询所有班级
     *
     * @return <code>List</code> 包含 AClass 类型的数组
     * @see List
     */
    public static List<AClass> selectAllFromClass() throws NoConfigException, SQLException {
        return ClassOA.selectAll();
    }

    /**
     * 通过班级编号查询班级
     *
     * @param classID 班级编号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass selectFromClass(long classID) throws NoConfigException, SQLException {
        return ClassOA.select(classID);
    }

    /**
     * 通过专业ID，年级，班号查询班级
     *
     * @param majorID 专业编号
     * @param grade 年级
     * @param classNum 班号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass selectFromClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.select(majorID, grade, classNum);
    }

    /**
     * 插入班级
     *
     * @param majorID 隶属专业
     * @param grade 年级
     * @param classNum 班号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass insertIntoClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return ClassOA.insert(majorID, grade, classNum);
    }

    /**
     * 插入班级
     *
     * @param majorName 隶属专业
     * @param grade 年级
     * @param classNum 班号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass insertIntoClass(String majorName, int grade, int classNum) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return ClassOA.insert(majorName, grade, classNum);
    }

    /**
     * 插入班级（指定班级编号）
     *
     * @param classID 班级编号
     * @param majorID 隶属专业
     * @param grade 年级
     * @param classNum 班号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass insertIntoClass(long classID, int majorID, int grade, int classNum) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return ClassOA.insert(classID, majorID, grade, classNum);
    }

    /**
     * 插入班级（指定班级编号）
     *
     * @param classID 班级编号
     * @param majorName 隶属专业
     * @param grade 年级
     * @param classNum 班号
     * @return <code>AClass</code> 班级类
     * @see AClass
     */
    public static AClass insertIntoClass(long classID, String majorName, int grade, int classNum) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return ClassOA.insert(classID, majorName, grade, classNum);
    }

    /**
     * 通过班级编号查询班级是否存在
     *
     * @param classID 班级编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInClass(long classID) throws NoConfigException, SQLException {
        return ClassOA.isExists(classID);
    }

    /**
     * 通过隶属专业编号，年级，班号查询班级是否存在
     * @param majorID 专业编号
     * @param grade 年级
     * @param classNum 班号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.isExists(majorID, grade, classNum);
    }

    /**
     * 通过班级编号删除班级
     *
     * @param classID 班级编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromClass(long classID) throws NoConfigException, SQLException {
        return ClassOA.delete(classID);
    }

    /**
     * 通过隶属专业编号，年级，班号
     *
     * @param majorID 隶属专业
     * @param grade 年级
     * @param classNum 班号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.delete(majorID, grade, classNum);
    }

    /**
     * 查询所有学生
     *
     * @return <code>List</code> 包含 Student 类型的数组
     * @see List
     */
    public static List<Student> selectAllFromStudent() throws NoConfigException, SQLException {
        return StudentOA.selectAll();
    }

    /**
     * 通过学生编号查询学生
     *
     * @param stuID 学生编号
     * @return <code>Student</code> 学生类
     * @see Student
     */
    public static Student selectFromStudent(long stuID) throws NoConfigException, SQLException {
        return StudentOA.select(stuID);
    }

    /**
     * 学生检验密码
     *
     * @param stuID 学生编号
     * @param passwd 密码
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean checkPasswdInStudent(long stuID, String passwd) throws NoConfigException, SQLException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return StudentOA.checkPasswd(stuID, passwd);
    }

    /**
     * 学生更改密码
     *
     * @param stuID 学生编号
     * @param passwd 密码
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean changePasswdInStudent(long stuID, String passwd) throws NoConfigException, SQLException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return StudentOA.changePasswd(stuID, passwd);
    }

    /**
     * 插入学生
     *
     * @param stuID 学号
     * @param stuName 学生姓名
     * @param gender 性别
     * @param classID 隶属班级
     * @param passwd 密码
     * @return <code>Student</code> 学生类
     * @see Student
     */
    public static Student insertIntoStudent(long stuID, String stuName, Gender gender, long classID, String passwd) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return StudentOA.insert(stuID, stuName, gender, classID, passwd);
    }

    /**
     * 通过学生编号查询学生是否存在
     *
     * @param stuID 学生编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInStudent(long stuID) throws NoConfigException, SQLException {
        return StudentOA.isExists(stuID);
    }

    /**
     * 通过学生编号删除学生
     *
     * @param stuID 学生编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromStudent(long stuID) throws NoConfigException, SQLException {
        return StudentOA.delete(stuID);
    }

    /**
     * 查询所有教师
     *
     * @return <code>List</code> 包含 Teacher 类型的数组
     * @see List
     */
    public static List<Teacher> selectAllFromTeacher() throws NoConfigException, SQLException {
        return TeacherOA.selectAll();
    }

    /**
     * 通过教师编号查询教师
     *
     * @param tchID 教师编号
     * @return <code>Teacher</code> 教师类
     * @see Teacher
     */
    public static Teacher selectFromTeacher(int tchID) throws NoConfigException, SQLException {
        return TeacherOA.select(tchID);
    }

    /**
     * 教师检验密码
     *
     * @param tchID 教师编号
     * @param passwd 密码
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean checkPasswdInTeacher(long tchID, String passwd) throws NoConfigException, SQLException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return TeacherOA.checkPasswd(tchID, passwd);
    }

    /**
     * 教师更改密码
     *
     * @param tchID 教师编号
     * @param passwd 密码
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean changePasswdInTeacher(long tchID, String passwd) throws NoConfigException, SQLException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return TeacherOA.changePasswd(tchID, passwd);
    }

    /**
     * 插入教师
     *
     * @param tchID 教师编号
     * @param tchName 教师姓名
     * @param gender 性别
     * @param facID 隶属院系
     * @param passwd 密码
     * @return <code>Teacher</code> 教师类
     * @see Teacher
     */
    public static Teacher insertIntoTeacher(long tchID, String tchName, Gender gender, int facID, String passwd) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        return TeacherOA.insert(tchID, tchName, gender, facID, passwd);
    }

    /**
     * 通过教师编号查询教师是否存在
     *
     * @param tchID 教师编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInTeacher(long tchID) throws NoConfigException, SQLException {
        return TeacherOA.isExists(tchID);
    }

    /**
     * 通过教师编号删除教师
     *
     * @param tchID 教师编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromTeacher(long tchID) throws NoConfigException, SQLException {
        return TeacherOA.delete(tchID);
    }

    /**
     * 查询所有课程
     *
     * @return <code>List</code> 包含 Course 类型的数组
     * @see List
     */
    public static List<Course> selectAllFromCourse() throws NoConfigException, SQLException {
        return CourseOA.selectAll();
    }

    /**
     * 通过课程编号查询课程
     *
     * @param courID 课程编号
     * @return <code>Course</code> 课程类
     * @see Course
     */
    public static Course selectFromCourse(long courID) throws NoConfigException, SQLException {
        return CourseOA.select(courID);
    }

    /**
     * 插入课程
     *
     * @param courID 课程编号
     * @param subID 隶属专业
     * @param tchID 隶属教师
     * @param courTimeStart 课程开始时间
     * @param courTimeEnd 课程结束时间
     * @return <code>Course</code> 课程类
     * @see Course
     */
    public static Course insertIntoCourse(long courID, int subID, long tchID, LocalDateTime courTimeStart, LocalDateTime courTimeEnd) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return CourseOA.insert(courID, subID, tchID, courTimeStart, courTimeEnd);
    }

    /**
     * 通过课程编号查询课程是否存在
     *
     * @param courID 课程编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInCourse(long courID) throws NoConfigException, SQLException {
        return CourseOA.isExists(courID);
    }

    /**
     * 通过课程编号删除课程
     *
     * @param courID 课程编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromCourse(long courID) throws NoConfigException, SQLException {
        return CourseOA.delete(courID);
    }

    /**
     * 查询所有考勤
     *
     * @return <code>List</code> 包含 Attendance 类型的数组
     * @see List
     */
    public static List<Attendance> selectAllFromAttendance() throws NoConfigException, SQLException {
        return AttendanceOA.selectAll();
    }

    /**
     * 通过考勤编号查询考勤
     *
     * @param attID 考勤编号
     * @return <code>Attendance</code> 考勤类
     * @see Attendance
     */
    public static Attendance selectFromAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.select(attID);
    }

    /**
     * 通过学生编号和课程编号查询考勤
     *
     * @param stuID 学生编号
     * @param courID 课程编号
     * @return <code>Attendance</code> 考勤类
     * @see Attendance
     */
    public static Attendance selectFromAttendance(long stuID, long courID) throws NoConfigException, SQLException {
        return AttendanceOA.select(stuID, courID);
    }

    /**
     * 插入考勤
     *
     * @param courID 隶属课程
     * @param stuID 隶属学生
     * @param attTime 考勤时间
     * @param attStatus 考勤状态
     * @return <code>Attendance</code> 考勤类
     * @see Attendance
     */
    public static Attendance insertIntoAttendance(long courID, long stuID, @Nullable LocalDateTime attTime, AttStatus attStatus) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return AttendanceOA.insert(courID, stuID, attTime, attStatus);
    }

    /**
     * 通过考勤编号查询考勤是否存在
     * @param attID 考勤编号
     * @return <code>true</code> 存在
     * <code>false</code> 不存在
     */
    public static boolean isExistsInAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.isExists(attID);
    }

    /**
     * 通过考勤编号删除考勤
     *
     * @param attID 考勤编号
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean deleteFromAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.delete(attID);
    }

    /**
     * 查询教师所授课程
     *
     * @param tchID 教师编号
     * @return <code>List</code> 包含 Course 类型的数组
     * @see List
     */
    public static List<Course> queryCourses(long tchID) throws NoConfigException, SQLException, DependenciesNotFoundException {
        if (!TeacherOA.isExists(tchID)) throw new DependenciesNotFoundException();
        ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT courID FROM course,teacher WHERE course.tchID=teacher.tchID AND course.tchID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, tchID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        courses.add(CourseOA.select(resultSet.getLong("courID")));
                    }
                }
            }
        }
        return courses;
    }

    /**
     * 查询课程所有学生
     *
     * @param courID 课程编号
     * @return <code>List</code> 包含 Student 类型的数组
     * @see List
     */
    public static List<Student> selectStudentsFromCourse(long courID) throws DependenciesNotFoundException, NoConfigException, SQLException {
        if (!CourseOA.isExists(courID)) throw new DependenciesNotFoundException();

        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT stuID FROM attendance WHERE courID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        students.add(StudentOA.select(resultSet.getLong("stuID")));
                    }
                }
            }
        }
        return students;
    }

    /**
     * 更新考勤状态
     *
     * @param attID 考勤编号
     * @param attStatus 考勤状态
     * @param attTime 考勤时间（可空）
     * @return <code>true</code> 成功
     * <code>false</code> 失败
     */
    public static boolean updateAttendance(String attID, AttStatus attStatus, @Nullable LocalDateTime attTime) throws NoConfigException, SQLException, DependenciesNotFoundException {
        if (!AttendanceOA.isExists(attID)) throw new DependenciesNotFoundException();

        String sql = "UPDATE attendance SET attStatus=?,attTime=? WHERE attID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, attStatus.name());
                if (attTime == null) {
                    preparedStatement.setTimestamp(2, null);
                } else {
                    preparedStatement.setTimestamp(2, new Timestamp(attTime.toEpochSecond(ZoneOffset.of("+8")) * 1000));
                }
                preparedStatement.setString(3, attID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }

    /**
     * 查询课程考勤
     * @param courID 课程编号
     * @return <code>List</code> 包含 Attendance 类型的数组
     * @see List
     */
    public static List<Attendance> selectAttendanceByCourse(long courID) throws NoConfigException, SQLException, DependenciesNotFoundException {
        if (!CourseOA.isExists(courID)) throw new DependenciesNotFoundException();

        ArrayList<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT attID FROM attendance WHERE courID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        attendances.add(AttendanceOA.select(resultSet.getString("attID")));
                    }
                }
            }
        }
        return attendances;
    }

    /**
     * 关闭数据库
     */
    public static void close() {
        PoolHelper.close();
    }
}
