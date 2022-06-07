package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CommonOA {
    public static boolean isExists(String sql, int id) throws NoConfigException, SQLException {
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isExists(String sql, String name) throws NoConfigException, SQLException {
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }
}
