package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.bean.AttStatus;
import com.cfive.classroom.library.database.bean.Gender;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseInsertTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    void insertSubject() throws IOException {
        getFromFile("subject.CSV").forEach(s -> {
            int subID = Integer.parseInt(s.substring(0, s.indexOf(",")));
            String subName = s.substring(s.indexOf(",") + 1);
            try {
                DatabaseHelper.insertIntoSubject(subID, subName);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException e) {
                LOGGER.error(e);
            }
        });
    }

    @Test
    void insertTeacher() throws IOException {
        getFromFile("teacher.CSV").forEach(s -> {
            long tchID = Long.parseLong(s.substring(0, s.indexOf(",")));
            String tchName = s.substring(s.indexOf(",") + 1, s.indexOf(",", s.indexOf(",") + 1));
            String gander = s.substring(s.indexOf(",", s.indexOf(",") + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1));
            int facID = Integer.parseInt(s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1)));
            String passwd = s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1) + 1);
            try {
                DatabaseHelper.insertIntoTeacher(tchID, tchName, gander.equals("m") ? Gender.m : Gender.f, facID, passwd);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void insertMajor() throws IOException {
        getFromFile("major.CSV").forEach(s -> {
            int facID = Integer.parseInt(s.substring(0, s.indexOf(",")));
            String majorName = s.substring(s.indexOf(",") + 1);
            try {
                DatabaseHelper.insertIntoMajor(majorName, facID);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException e) {
                LOGGER.error(e);
            }
        });
    }

    @Test
    void insertClass() throws IOException {
        getFromFile("class.CSV").forEach(s -> {
            int majorID = Integer.parseInt(s.substring(0, s.indexOf(",")));
            int grade = Integer.parseInt(s.substring(s.indexOf(",") + 1, s.indexOf(",", s.indexOf(",") + 1)));
            int classNum = Integer.parseInt(s.substring(s.indexOf(",", s.indexOf(",") + 1) + 1));
            try {
                DatabaseHelper.insertIntoClass(majorID, grade, classNum);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException e) {
                LOGGER.error(e);
            }
        });
    }

    @Test
    void insertStudent() throws IOException {
        getFromFile("student.CSV").forEach(s -> {
            long stuID = Long.parseLong(s.substring(0, s.indexOf(",")));
            String stuName = s.substring(s.indexOf(",") + 1, s.indexOf(",", s.indexOf(",") + 1));
            String gander = s.substring(s.indexOf(",", s.indexOf(",") + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1));
            long classID = Long.parseLong(s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1)));
            String passwd = s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1) + 1);
            try {
                DatabaseHelper.insertIntoStudent(stuID, stuName, gander.equals("m") ? Gender.m : Gender.f, classID, passwd);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void insertCourse() throws IOException {
        getFromFile("course.CSV").forEach(s -> {
            long courID = Long.parseLong(s.substring(0, s.indexOf(",")));
            int subID = Integer.parseInt(s.substring(s.indexOf(",") + 1, s.indexOf(",", s.indexOf(",") + 1)));
            long tchID = Long.parseLong(s.substring(s.indexOf(",", s.indexOf(",") + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1)));
            LocalDateTime courTimeFrom = LocalDateTime.parse(s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1, s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1)), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
            LocalDateTime courTimeEnd = LocalDateTime.parse(s.substring(s.indexOf(",", s.indexOf(",", s.indexOf(",", s.indexOf(",") + 1) + 1) + 1) + 1), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
            try {
                DatabaseHelper.insertIntoCourse(courID, subID, tchID, courTimeFrom, courTimeEnd);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void insertAtt() throws IOException {
        getFromFile("att.CSV").forEach(s -> {
            long courID = Long.parseLong(s.substring(0, s.indexOf(",")));
            long stuID = Long.parseLong(s.substring(s.indexOf(",") + 1));
            try {
                DatabaseHelper.insertIntoAttendance(courID, stuID, null, AttStatus.not_signed);
            } catch (NoConfigException | SQLException | InsertException | AlreadyExistsException |
                     DependenciesNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static ArrayList<String> getFromFile(String path) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        File csv = new File(path);

        try (FileReader fileReader = new FileReader(csv)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String lineData;
                while ((lineData = bufferedReader.readLine()) != null) {
                    strings.add(lineData);
                }
            }
        }
        return strings;
    }
}
