package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.bean.AClass;
import com.cfive.classroom.library.database.bean.Faculty;
import com.cfive.classroom.library.database.bean.Major;
import com.cfive.classroom.library.database.bean.Subject;
import com.cfive.classroom.library.database.operation.ClassOA;
import com.cfive.classroom.library.database.operation.FacultyOA;
import com.cfive.classroom.library.database.operation.MajorOA;
import com.cfive.classroom.library.database.operation.SubjectOA;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.SQLException;
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



    public static void close() {
        PoolHelper.close();
    }
}
