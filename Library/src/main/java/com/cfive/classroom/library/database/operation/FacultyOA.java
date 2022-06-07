package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.Faculty;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyOA extends CommonOA {
    public static List<Faculty> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT * FROM faculty ORDER BY facID";
        ArrayList<Faculty> faculties = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        faculties.add(new Faculty(resultSet.getInt("facID"), resultSet.getString("facName")));
                    }
                }
            }
        }
        return faculties;
    }

    public static Faculty select(int facID) throws SQLException, NoConfigException {
        String sql = "SELECT * FROM faculty WHERE facID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, facID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                    }
                }
            }
        }
        return null;
    }

    public static Faculty select(String facName) throws SQLException, NoConfigException {
        String sql = "SELECT * FROM faculty WHERE facName=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, facName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                    }
                }
            }
        }
        return null;
    }

    public static Faculty insert(String facName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        if (isExists(facName)) throw new AlreadyExistsException();
        String sql = "INSERT INTO faculty(facName) VALUES (?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, facName);
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new Faculty(generatedKeys.getInt(1), facName);
                    } else {
                        throw new InsertException();
                    }
                }
            }
        }
    }

    public static Faculty insert(int facID, String facName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        if (isExists(facName)) throw new AlreadyExistsException();
        String sql = "INSERT INTO faculty VALUES (?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, facID);
                preparedStatement.setString(2, facName);
                if (preparedStatement.executeUpdate()==1) {
                    return new Faculty(facID, facName);
                }
                throw new InsertException();
            }
        }
    }

    public static boolean isExists(int facID) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM faculty WHERE facID=?)";
        return isExists(sql, facID);
    }

    public static boolean isExists(String facName) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM faculty WHERE facName=?)";
        return isExists(sql, facName);
    }

    public static boolean delete(int facID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM faculty WHERE facID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, facID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }

    public static boolean delete(String facName) throws NoConfigException, SQLException {
        String sql = "DELETE FROM faculty WHERE facName=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, facName);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
