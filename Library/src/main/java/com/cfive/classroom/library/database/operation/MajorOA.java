package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.Faculty;
import com.cfive.classroom.library.database.bean.Major;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorOA extends CommonOA {
    public static List<Major> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT majorID,majorName,faculty.facID,facName FROM major,faculty where major.facID=faculty.facID ORDER BY majorID";
        ArrayList<Major> majors = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        majors.add(new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"))));
                    }
                }
            }
        }
        return majors;
    }

    public static Major select(int majorID) throws SQLException, NoConfigException {
        String sql = "SELECT majorID,majorName,faculty.facID,facName FROM major,faculty WHERE major.facID=faculty.facID AND majorID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, majorID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), new Faculty(resultSet.getInt("facID"), resultSet.getString("facName")));
                    }
                }
            }
        }
        return null;
    }

    public static Major select(String majorName) throws SQLException, NoConfigException {
        String sql = "SELECT majorID,majorName,faculty.facID,facName FROM major,faculty WHERE major.facID=faculty.facID AND majorName=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, majorName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), new Faculty(resultSet.getInt("facID"), resultSet.getString("facName")));
                    }
                }
            }
        }
        return null;
    }

    public static Major insert(String majorName, int facID) throws NoConfigException, InsertException, SQLException, AlreadyExistsException, DependenciesNotFoundException {
        return insert(majorName, FacultyOA.isExists(facID), FacultyOA.select(facID));
    }

    public static Major insert(String majorName, String facName) throws InsertException, SQLException, NoConfigException, AlreadyExistsException, DependenciesNotFoundException {
        return insert(majorName, FacultyOA.isExists(facName), FacultyOA.select(facName));
    }

    private static Major insert(String majorName, boolean isExistsFaculty, Faculty faculty) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(majorName)) throw new AlreadyExistsException();
        if (!isExistsFaculty) throw new DependenciesNotFoundException();
        String sql = "INSERT INTO major(majorName, facID) VALUES (?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, majorName);
                assert faculty != null;
                preparedStatement.setInt(2, faculty.getFacID());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new Major(generatedKeys.getInt(1), majorName, faculty);
                    } else {
                        throw new InsertException();
                    }
                }
            }
        }
    }

    public static Major insert(int majorID, String majorName, int facID) throws NoConfigException, InsertException, SQLException, AlreadyExistsException, DependenciesNotFoundException {
        return insert(majorID, majorName, FacultyOA.isExists(facID), FacultyOA.select(facID));
    }

    public static Major insert(int majorID, String majorName, String facName) throws InsertException, SQLException, NoConfigException, AlreadyExistsException, DependenciesNotFoundException {
        return insert(majorID, majorName, FacultyOA.isExists(facName), FacultyOA.select(facName));
    }

    private static Major insert(int majorID, String majorName, boolean isExistsFaculty, Faculty faculty) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(majorName)) throw new AlreadyExistsException();
        if (!isExistsFaculty) throw new DependenciesNotFoundException();
        String sql = "INSERT INTO major VALUES (?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, majorID);
                preparedStatement.setString(2, majorName);
                assert faculty != null;
                preparedStatement.setInt(3, faculty.getFacID());
                if (preparedStatement.executeUpdate() == 1) {
                    return new Major(majorID, majorName, faculty);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(int majorID) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM major WHERE majorID=?)";
        return isExists(sql, majorID);
    }

    public static boolean isExists(String majorName) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM major WHERE majorName=?)";
        return isExists(sql, majorName);
    }

    public static boolean delete(int majorID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM major WHERE majorID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, majorID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }

    public static boolean delete(String majorName) throws NoConfigException, SQLException {
        String sql = "DELETE FROM major WHERE majorID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, majorName);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
