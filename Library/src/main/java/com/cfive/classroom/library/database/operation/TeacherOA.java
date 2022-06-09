package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherOA {
    public static List<Teacher> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT tchID,tchName,tchGender,passwd,salt,faculty.facID,facName FROM teacher,faculty where teacher.facID=faculty.facID ORDER BY tchID";
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        teachers.add(new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m")?Gender.m:Gender.f, faculty, resultSet.getString("passwd"), resultSet.getString("salt")));
                    }
                }
            }
        }
        return teachers;
    }

    public static Teacher select(long tchID) throws SQLException, NoConfigException {
        String sql = "SELECT tchID,tchName,tchGender,passwd,salt,faculty.facID,facName FROM teacher,faculty where teacher.facID=faculty.facID AND tchID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, tchID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        return new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m")?Gender.m:Gender.f, faculty, resultSet.getString("passwd"), resultSet.getString("salt"));
                    }
                }
            }
        }
        return null;
    }

    public static Teacher insert(long tchID, String tchName, Gender gender, int facID, String passwd, String salt) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(tchID)) throw new AlreadyExistsException();
        if (!FacultyOA.isExists(facID)) throw new DependenciesNotFoundException();

        String sql = "INSERT INTO teacher VALUES (?,?,?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, tchID);
                preparedStatement.setString(2, tchName);
                preparedStatement.setString(3, gender.name());
                preparedStatement.setInt(4, facID);
                preparedStatement.setString(5, passwd);
                preparedStatement.setString(6, salt);
                if (preparedStatement.executeUpdate() == 1) {
                    return new Teacher(tchID, tchName, gender, FacultyOA.select(facID), passwd, salt);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(long tchID) throws SQLException, NoConfigException {
        String sql = "SELECT EXISTS(SELECT 1 FROM teacher WHERE tchID=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, tchID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean delete(long tchID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM teacher WHERE tchID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, tchID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
