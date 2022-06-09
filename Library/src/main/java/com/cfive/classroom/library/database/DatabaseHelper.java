package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.operation.*;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.sun.istack.internal.Nullable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DatabaseHelper {
    public static List<Faculty> selectAllFromFaculty() throws NoConfigException, SQLException {
        return FacultyOA.selectAll();
    }

    public static Faculty selectFromFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.select(facID);
    }

    public static Faculty selectFromFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.select(facName);
    }

    public static Faculty insertIntoFaculty(String facName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return FacultyOA.insert(facName);
    }

    public static Faculty insertIntoFaculty(int facID, String facName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        return FacultyOA.insert(facID, facName);
    }

    public static boolean isExistsInFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.isExists(facID);
    }

    public static boolean isExistsInFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.isExists(facName);
    }

    public static boolean deleteFromFaculty(int facID) throws NoConfigException, SQLException {
        return FacultyOA.delete(facID);
    }

    public static boolean deleteFromFaculty(String facName) throws NoConfigException, SQLException {
        return FacultyOA.delete(facName);
    }

    public static List<Subject> selectAllFromSubject() throws NoConfigException, SQLException {
        return SubjectOA.selectAll();
    }

    public static Subject selectFromSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.select(subID);
    }

    public static Subject selectFromSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.select(subName);
    }

    public static Subject insertIntoSubject(String subName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return SubjectOA.insert(subName);
    }

    public static Subject insertIntoSubject(int subID, String subName) throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        return SubjectOA.insert(subID, subName);
    }

    public static boolean isExistsInSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.isExists(subID);
    }

    public static boolean isExistsInSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.isExists(subName);
    }

    public static boolean deleteFromSubject(int subID) throws NoConfigException, SQLException {
        return SubjectOA.delete(subID);
    }

    public static boolean deleteFromSubject(String subName) throws NoConfigException, SQLException {
        return SubjectOA.delete(subName);
    }

    public static List<Major> selectAllFromMajor() throws NoConfigException, SQLException {
        return MajorOA.selectAll();
    }

    public static Major selectFromMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.select(majorID);
    }

    public static Major selectFromMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.select(majorName);
    }

    public static Major insertIntoMajor(String majorName, int facID) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return MajorOA.insert(majorName, facID);
    }

    public static Major insertIntoMajor(String majorName, String facName) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return MajorOA.insert(majorName, facName);
    }

    public static Major insertIntoMajor(int majorID, String majorName, int facID) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return MajorOA.insert(majorID, majorName, facID);
    }

    public static Major insertIntoMajor(int majorID, String majorName, String facName) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return MajorOA.insert(majorID, majorName, facName);
    }

    public static boolean isExistsInMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.isExists(majorID);
    }

    public static boolean isExistsInMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.isExists(majorName);
    }

    public static boolean deleteFromMajor(int majorID) throws NoConfigException, SQLException {
        return MajorOA.delete(majorID);
    }

    public static boolean deleteFromMajor(String majorName) throws NoConfigException, SQLException {
        return MajorOA.delete(majorName);
    }

    public static List<AClass> selectAllFromClass() throws NoConfigException, SQLException {
        return ClassOA.selectAll();
    }

    public static AClass selectFromClass(int classID) throws NoConfigException, SQLException {
        return ClassOA.select(classID);
    }

    public static AClass selectFromClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.select(majorID, grade, classNum);
    }

    public static AClass insertIntoClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return ClassOA.insert(majorID, grade, classNum);
    }

    public static AClass insertIntoClass(String majorName, int grade, int classNum) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return ClassOA.insert(majorName, grade, classNum);
    }

    public static AClass insertIntoClass(long classID, int majorID, int grade, int classNum) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return ClassOA.insert(classID, majorID, grade, classNum);
    }

    public static AClass insertIntoClass(long classID, String majorName, int grade, int classNum) throws NoConfigException, AlreadyExistsException, SQLException, InsertException, DependenciesNotFoundException {
        return ClassOA.insert(classID, majorName, grade, classNum);
    }

    public static boolean isExistsInClass(int classID) throws NoConfigException, SQLException {
        return ClassOA.isExists(classID);
    }

    public static boolean isExistsInClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.isExists(majorID, grade, classNum);
    }

    public static boolean deleteFromClass(int classID) throws NoConfigException, SQLException {
        return ClassOA.delete(classID);
    }

    public static boolean deleteFromClass(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        return ClassOA.delete(majorID, grade, classNum);
    }

    public static List<Student> selectAllFromStudent() throws NoConfigException, SQLException {
        return StudentOA.selectAll();
    }

    public static Student selectFromStudent(int stuID) throws NoConfigException, SQLException {
        return StudentOA.select(stuID);
    }

    public static Student insertIntoStudent(long stuID, String stuName, Gender gender, long classID, String passwd, String salt) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return StudentOA.insert(stuID, stuName, gender, classID, passwd, salt);
    }

    public static boolean isExistsInStudent(int stuID) throws NoConfigException, SQLException {
        return StudentOA.isExists(stuID);
    }

    public static boolean deleteFromStudent(int stuID) throws NoConfigException, SQLException {
        return StudentOA.delete(stuID);
    }

    public static List<Teacher> selectAllFromTeacher() throws NoConfigException, SQLException {
        return TeacherOA.selectAll();
    }

    public static Teacher selectFromTeacher(int tchID) throws NoConfigException, SQLException {
        return TeacherOA.select(tchID);
    }

    public static Teacher insertIntoTeacher(long tchID, String tchName, Gender gender, int facID, String passwd, String salt) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return TeacherOA.insert(tchID, tchName, gender, facID, passwd, salt);
    }

    public static boolean isExistsInTeacher(int tchID) throws NoConfigException, SQLException {
        return TeacherOA.isExists(tchID);
    }

    public static boolean deleteFromTeacher(int tchID) throws NoConfigException, SQLException {
        return TeacherOA.delete(tchID);
    }

    public static List<Course> selectAllFromCourse() throws NoConfigException, SQLException {
        return CourseOA.selectAll();
    }

    public static Course selectFromCourse(int courID) throws NoConfigException, SQLException {
        return CourseOA.select(courID);
    }

    public static Course insertIntoCourse(long courID, int subID, long tchID, LocalDateTime courTimeStart, LocalDateTime courTimeEnd) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return CourseOA.insert(courID, subID, tchID, courTimeStart, courTimeEnd);
    }

    public static boolean isExistsInCourse(int courID) throws NoConfigException, SQLException {
        return CourseOA.isExists(courID);
    }

    public static boolean deleteFromCourse(int courID) throws NoConfigException, SQLException {
        return CourseOA.delete(courID);
    }

    public static List<Attendance> selectAllFromAttendance() throws NoConfigException, SQLException {
        return AttendanceOA.selectAll();
    }

    public static Attendance selectFromAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.select(attID);
    }

    public static Attendance insertIntoAttendance(long courID, long stuID, @Nullable LocalDateTime attTime, AttStatus attStatus) throws NoConfigException, SQLException, InsertException, AlreadyExistsException, DependenciesNotFoundException {
        return AttendanceOA.insert(courID, stuID, attTime, attStatus);
    }

    public static boolean isExistsInAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.isExists(attID);
    }

    public static boolean deleteFromAttendance(String attID) throws NoConfigException, SQLException {
        return AttendanceOA.delete(attID);
    }

    public static void close() {
        PoolHelper.close();
    }
}
