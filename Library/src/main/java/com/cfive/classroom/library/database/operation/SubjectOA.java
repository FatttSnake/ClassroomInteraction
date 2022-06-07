package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.Subject;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectOA extends CommonOA {
    public static List<Subject> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT * FROM subject ORDER BY subID";
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        subjects.add(new Subject(resultSet.getInt("subID"), resultSet.getString("subName")));
                    }
                }
            }
        }
        return subjects;
    }

    public static Subject select(int subID) throws SQLException, NoConfigException {
        String sql = "SELECT * FROM subject WHERE subID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, subID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                    }
                }
            }
        }
        return null;
    }

    public static Subject select(String subName) throws SQLException, NoConfigException {
        String sql = "SELECT * FROM subject WHERE subName=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, subName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                    }
                }
            }
        }
        return null;
    }

    public static Subject insert(String subName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        if (isExists(subName)) throw new AlreadyExistsException();
        String sql = "INSERT INTO subject(subName) VALUES (?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, subName);
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new Subject(generatedKeys.getInt(1), subName);
                    } else {
                        throw new InsertException();
                    }
                }
            }
        }
    }

    public static Subject insert(int subID, String subName) throws NoConfigException, InsertException, SQLException, AlreadyExistsException {
        if (isExists(subName)) throw new AlreadyExistsException();
        String sql = "INSERT INTO subject VALUES (?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, subID);
                preparedStatement.setString(2, subName);
                if (preparedStatement.executeUpdate()==1) {
                    return new Subject(subID, subName);
                }
                throw new InsertException();
            }
        }
    }

    public static boolean isExists(int subID) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM subject WHERE subID=?)";
        return isExists(sql, subID);
    }

    public static boolean isExists(String subName) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM subject WHERE subName=?)";
        return isExists(sql, subName);
    }

    public static boolean delete(int subID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM subject WHERE subID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, subID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }

    public static boolean delete(String subName) throws NoConfigException, SQLException {
        String sql = "DELETE FROM subject WHERE subName=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, subName);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
