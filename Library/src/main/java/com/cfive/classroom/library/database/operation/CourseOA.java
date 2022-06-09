package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.util.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CourseOA {
    public static List<Course> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT courID,courTimeFrom,courTimeEnd,subject.subID,teacher.tchID,tchName,tchGender,passwd,salt,faculty.facID,facName FROM course,subject,teacher,faculty where course.subID=subject.subID AND course.tchID=teacher.tchID AND teacher.facID=faculty.facID ORDER BY courID";
        ArrayList<Course> courses = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        Subject subject = new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Teacher teacher = new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m") ? Gender.m : Gender.f, faculty, resultSet.getString("passwd"), resultSet.getString("salt"));
                        courses.add(new Course(resultSet.getLong("courID"), subject, teacher, LocalDateTime.ofEpochSecond(resultSet.getLong("courTimeFrom"), 0, ZoneOffset.UTC), LocalDateTime.ofEpochSecond(resultSet.getLong("courTimeEnd"), 0, ZoneOffset.UTC)));
                    }
                }
            }
        }
        return courses;
    }

    public static Course select(long courID) throws SQLException, NoConfigException {
        String sql = "SELECT courID,courTimeFrom,courTimeEnd,subject.subID,teacher.tchID,tchName,tchGender,passwd,salt,faculty.facID,facName FROM course,subject,teacher,faculty where course.subID=subject.subID AND course.tchID=teacher.tchID AND teacher.facID=faculty.facID AND courID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Subject subject = new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Teacher teacher = new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m") ? Gender.m : Gender.f, faculty, resultSet.getString("passwd"), resultSet.getString("salt"));
                        return new Course(resultSet.getLong("courID"), subject, teacher, LocalDateTime.ofEpochSecond(resultSet.getLong("courTimeFrom"), 0, ZoneOffset.UTC), LocalDateTime.ofEpochSecond(resultSet.getLong("courTimeEnd"), 0, ZoneOffset.UTC));
                    }
                }
            }
        }
        return null;
    }

    public static Course insert(long courID, int subID, long tchID, LocalDateTime courTimeStart, LocalDateTime courTimeEnd) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(courID)) throw new AlreadyExistsException();
        if (!SubjectOA.isExists(subID) || !TeacherOA.isExists(tchID)) throw new DependenciesNotFoundException();

        String sql = "INSERT INTO course VALUES (?,?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                preparedStatement.setInt(2, subID);
                preparedStatement.setLong(3, tchID);
                preparedStatement.setLong(4, courTimeStart.toEpochSecond(ZoneOffset.UTC));
                preparedStatement.setLong(5, courTimeEnd.toEpochSecond(ZoneOffset.UTC));
                if (preparedStatement.executeUpdate() == 1) {
                    return new Course(courID, SubjectOA.select(subID), TeacherOA.select(tchID), courTimeStart, courTimeEnd);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(long courID) throws SQLException, NoConfigException {
        String sql = "SELECT EXISTS(SELECT 1 FROM course WHERE courID=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean delete(long courID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM course WHERE courID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, courID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
