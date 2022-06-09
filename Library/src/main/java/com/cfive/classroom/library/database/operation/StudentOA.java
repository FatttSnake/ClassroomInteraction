package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.util.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentOA {
    public static List<Student> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT stuID,stuName,stuGender,passwd,salt,class.classID,grade,classNum,major.majorID,majorName,faculty.facID,facName FROM student,class,major,faculty where student.classID=class.classID AND class.majorID=major.majorID AND major.facID=faculty.facID ORDER BY stuID";
        ArrayList<Student> students = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Major major = new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), faculty);
                        AClass aClass = new AClass(resultSet.getLong("classID"), major, resultSet.getInt("grade"), resultSet.getInt("classNum"));
                        students.add(new Student(resultSet.getLong("stuID"), resultSet.getString("stuName"), resultSet.getString("stuGender").equals("m") ? Gender.m : Gender.f, aClass, resultSet.getString("passwd"), resultSet.getString("salt")));
                    }
                }
            }
        }
        return students;
    }

    public static Student select(long stuID) throws SQLException, NoConfigException {
        String sql = "SELECT stuID,stuName,stuGender,passwd,salt,class.classID,grade,classNum,major.majorID,majorName,faculty.facID,facName FROM student,class,major,faculty where student.classID=class.classID AND class.majorID=major.majorID AND major.facID=faculty.facID AND stuID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, stuID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Major major = new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), faculty);
                        AClass aClass = new AClass(resultSet.getLong("classID"), major, resultSet.getInt("grade"), resultSet.getInt("classNum"));
                        return new Student(resultSet.getLong("stuID"), resultSet.getString("stuName"), resultSet.getString("stuGender").equals("m") ? Gender.m : Gender.f, aClass, resultSet.getString("passwd"), resultSet.getString("salt"));
                    }
                }
            }
        }
        return null;
    }

    public static boolean checkPasswd(long stuID, String passwd) throws DependenciesNotFoundException, NoConfigException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Student student = select(stuID);
        if (student == null) {
            throw new DependenciesNotFoundException();
        }
        PBKDF2Util pbkdf2Util = new PBKDF2Util();
        return pbkdf2Util.authenticate(passwd, student.getPassword(), student.getSalt());
    }

    public static Student insert(long stuID, String stuName, Gender gender, long classID, String passwd) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (isExists(stuID)) throw new AlreadyExistsException();
        if (!ClassOA.isExists(classID)) throw new DependenciesNotFoundException();

        PBKDF2Util pbkdf2Util = new PBKDF2Util();
        String salt = pbkdf2Util.generateSalt();
        String encryptedPassword = pbkdf2Util.getEncryptedPassword(passwd, salt);

        String sql = "INSERT INTO student VALUES (?,?,?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, stuID);
                preparedStatement.setString(2, stuName);
                preparedStatement.setString(3, gender.name());
                preparedStatement.setLong(4, classID);
                preparedStatement.setString(5, encryptedPassword);
                preparedStatement.setString(6, salt);
                if (preparedStatement.executeUpdate() == 1) {
                    return new Student(classID, stuName, gender, ClassOA.select(classID), passwd, salt);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(long stuID) throws SQLException, NoConfigException {
        String sql = "SELECT EXISTS(SELECT 1 FROM student WHERE stuID=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, stuID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean delete(long stuID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM student WHERE stuID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, stuID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
