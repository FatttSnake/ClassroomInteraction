package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.Faculty;
import com.cfive.classroom.library.database.bean.AClass;
import com.cfive.classroom.library.database.bean.Major;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassOA {
    public static List<AClass> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT classID,major.majorID,grade,classNum, majorname, faculty.facid, facname FROM class,major,faculty where class.majorID=major.majorID AND major.facID=faculty.facID ORDER BY classID";
        ArrayList<AClass> AClasses = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        AClasses.add(new AClass(resultSet.getLong("classID"), new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"))), resultSet.getInt("grade"), resultSet.getInt("classNum")));
                    }
                }
            }
        }
        return AClasses;
    }

    public static AClass select(long classID) throws SQLException, NoConfigException {
        String sql = "SELECT classID,major.majorID,grade,classNum, majorname, faculty.facid, facname FROM class,major,faculty where class.majorID=major.majorID AND major.facID=faculty.facID AND classID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, classID);
                AClass resultSet = getAClass(preparedStatement);
                if (resultSet != null) return resultSet;
            }
        }
        return null;
    }

    public static AClass select(int majorID, int grade, int classNum) throws SQLException, NoConfigException {
        String sql = "SELECT classID,major.majorID,grade,classNum, majorname, faculty.facid, facname FROM class,major,faculty where class.majorID=major.majorID AND major.facID=faculty.facID AND class.majorID=? AND grade=? AND classNum=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, majorID);
                preparedStatement.setInt(2, grade);
                preparedStatement.setInt(3, classNum);
                AClass resultSet = getAClass(preparedStatement);
                if (resultSet != null) return resultSet;
            }
        }
        return null;
    }

    private static AClass getAClass(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return new AClass(resultSet.getLong("classID"), new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"))), resultSet.getInt("grade"), resultSet.getInt("classNum"));
            }
        }
        return null;
    }

    public static AClass insert(int majorID, int grade, int classNum) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(majorID, grade, classNum)) throw new AlreadyExistsException();
        if (!MajorOA.isExists(majorID)) throw new DependenciesNotFoundException();

        String sql = "INSERT INTO class(majorID,grade,classNum) VALUES (?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, majorID);
                preparedStatement.setInt(2, grade);
                preparedStatement.setInt(3, classNum);
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new AClass(generatedKeys.getLong(1), MajorOA.select(majorID), grade, classNum);
                    } else {
                        throw new InsertException();
                    }
                }
            }
        }
    }

    public static AClass insert(String majorName, int grade, int classNum) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (!MajorOA.isExists(majorName)) throw new DependenciesNotFoundException();
        if (isExists(Objects.requireNonNull(MajorOA.select(majorName)).getMajorID(), grade, classNum)) throw new AlreadyExistsException();

        String sql = "INSERT INTO class(majorID,grade,classNum) VALUES (?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, Objects.requireNonNull(MajorOA.select(majorName)).getMajorID());
                preparedStatement.setInt(2, grade);
                preparedStatement.setInt(3, classNum);
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new AClass(generatedKeys.getLong(1), MajorOA.select(majorName), grade, classNum);
                    } else {
                        throw new InsertException();
                    }
                }
            }
        }
    }

    public static AClass insert(long classID, int majorID, int grade, int classNum) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (isExists(classID) || isExists(majorID, grade, classNum)) throw new AlreadyExistsException();
        if (!MajorOA.isExists(majorID)) throw new DependenciesNotFoundException();

        String sql = "INSERT INTO class VALUES (?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, classID);
                preparedStatement.setInt(2, majorID);
                preparedStatement.setInt(3, grade);
                preparedStatement.setInt(4, classNum);
                if (preparedStatement.executeUpdate() == 1) {
                    return new AClass(classID, MajorOA.select(majorID), grade, classNum);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static AClass insert(long classID, String majorName, int grade, int classNum) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (!MajorOA.isExists(majorName)) throw new DependenciesNotFoundException();
        if (isExists(classID) || isExists(Objects.requireNonNull(MajorOA.select(majorName)).getMajorID(), grade, classNum)) throw new AlreadyExistsException();

        String sql = "INSERT INTO class VALUES (?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, classID);
                preparedStatement.setInt(2, Objects.requireNonNull(MajorOA.select(majorName)).getMajorID());
                preparedStatement.setInt(3, grade);
                preparedStatement.setInt(4, classNum);
                if (preparedStatement.executeUpdate() == 1) {
                    return new AClass(classID, MajorOA.select(majorName), grade, classNum);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(long classID) throws SQLException, NoConfigException {
        String sql = "SELECT EXISTS(SELECT 1 FROM class WHERE classID=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, classID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isExists(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM class WHERE majorID=? AND grade=? AND classNUM=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, majorID);
                preparedStatement.setInt(2, grade);
                preparedStatement.setInt(3, classNum);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean delete(long classID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM class WHERE classID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, classID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }

    public static boolean delete(int majorID, int grade, int classNum) throws NoConfigException, SQLException {
        String sql = "DELETE FROM class WHERE majorID=? AND grade=? AND classNum=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, majorID);
                preparedStatement.setInt(2, grade);
                preparedStatement.setInt(3, classNum);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
