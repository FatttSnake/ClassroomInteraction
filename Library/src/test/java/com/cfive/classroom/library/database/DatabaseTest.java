package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.bean.Faculty;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class DatabaseTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    void poolTest() throws NoConfigException {
        String sql = "INSERT INTO Faculty(facName) VALUES (?)";

        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, "1计算机");
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        LOGGER.info("成功插入 facID=" + generatedKeys.getInt(1) + " facName=计算机");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PoolHelper.close();
    }

    @Test
    void selectAllFacultyTest() throws NoConfigException, SQLException {
        List<Faculty> faculties = DatabaseHelper.selectAllFromFaculty();
        faculties.forEach(LOGGER::debug);

        DatabaseHelper.close();
    }

    @Test
    void insertIntoFacultyTest() throws NoConfigException, SQLException, InsertException, AlreadyExistsException {
        Faculty faculty = DatabaseHelper.insertIntoFaculty("荣誉学院");
        LOGGER.debug(faculty.toString());
        DatabaseHelper.close();
    }

    @Test
    void existsFacultyTest() throws NoConfigException, SQLException {
        LOGGER.debug(DatabaseHelper.isExistsInFaculty("信息学院"));
        LOGGER.debug(DatabaseHelper.deleteFromFaculty(18));
    }

    @Test
    void deleteFacultyTest() throws NoConfigException, SQLException {
//        LOGGER.debug(DatabaseHelper.deleteFromFaculty("计算机学院"));
        LOGGER.debug(DatabaseHelper.deleteFromFaculty(19));
    }

    @Test
    void selectFromFacultyTest() throws NoConfigException, SQLException {
        LOGGER.debug(DatabaseHelper.selectFromFaculty("计算机学院"));
        LOGGER.debug(DatabaseHelper.selectFromFaculty(6));
    }

    @Test
    void selectAllFromStudentTest() throws NoConfigException, SQLException {
        DatabaseHelper.selectAllFromStudent().forEach(LOGGER::debug);
    }

    @Test
    void selectStudentsFromCourseTest() throws NoConfigException, SQLException, DependenciesNotFoundException {
        DatabaseHelper.selectStudentsFromCourse(2).forEach(LOGGER::debug);
    }

    @Test
    void checkPasswdInTeacherTest() throws NoConfigException, SQLException, DependenciesNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        LOGGER.debug(DatabaseHelper.checkPasswdInTeacher(1002, "10191019"));
        LOGGER.debug(DatabaseHelper.changePasswdInTeacher(1002, "10191019"));
    }

    @Test
    void selectAttendanceByCourseTest() throws NoConfigException, SQLException, DependenciesNotFoundException {
        DatabaseHelper.selectAttendanceByCourse(2).forEach(LOGGER::debug);
    }

    @Test
    void TempTest() {
        LOGGER.debug(String.valueOf(UUID.randomUUID()));
    }
}
