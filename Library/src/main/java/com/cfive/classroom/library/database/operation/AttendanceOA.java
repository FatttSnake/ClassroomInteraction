package com.cfive.classroom.library.database.operation;

import com.cfive.classroom.library.database.PoolHelper;
import com.cfive.classroom.library.database.bean.*;
import com.cfive.classroom.library.database.util.AlreadyExistsException;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.InsertException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttendanceOA {
    public static List<Attendance> selectAll() throws NoConfigException, SQLException {
        String sql = "SELECT attID,attTime,attStatus,student.stuID,stuName,stuGender,student.passwd,student.salt,class.classID,grade,classNum,major.majorID,majorName,course.courID,courTimeFrom,courTimeEnd,subject.subID,subName,teacher.tchID,tchName,tchGender,teacher.passwd,teacher.salt,faculty.facID,facName FROM attendance,student,class,major,course,subject,teacher,faculty where attendance.courID=course.courID AND attendance.stuID=student.stuID AND class.classID=student.classID AND class.majorID=major.majorID AND major.facID=faculty.facID AND course.subID=subject.subID AND course.tchID=teacher.tchID AND teacher.facID=faculty.facID ORDER BY courID,attTime,attStatus";
        ArrayList<Attendance> attendances = new ArrayList<>();
        try (Connection connection = PoolHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        Subject subject = new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Teacher teacher = new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m") ? Gender.m : Gender.f, faculty, resultSet.getString("teacher.passwd"), resultSet.getString("teacher.salt"));
                        Course course = new Course(resultSet.getLong("courID"), subject, teacher, LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeFrom").getTime() / 1000, 0, ZoneOffset.of("+8")), LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeEnd").getTime() / 1000, 0, ZoneOffset.of("+8")));
                        Major major = new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), faculty);
                        AClass aClass = new AClass(resultSet.getLong("classID"), major, resultSet.getInt("grade"), resultSet.getInt("classNum"));
                        Student student = new Student(resultSet.getLong("stuID"), resultSet.getString("stuName"), resultSet.getString("stuGender").equals("m") ? Gender.m : Gender.f, aClass, resultSet.getString("student.passwd"), resultSet.getString("student.salt"));
                        attendances.add(new Attendance(resultSet.getString("attID"), course, student, LocalDateTime.ofEpochSecond(resultSet.getTimestamp("attTime").getTime() / 1000, 0, ZoneOffset.of("+8")), AttStatus.fromString(resultSet.getString("attStatus"))));
                    }
                }
            }
        }
        return attendances;
    }

    public static Attendance select(String attID) throws SQLException, NoConfigException {
        String sql = "SELECT attID,attTime,attStatus,student.stuID,stuName,stuGender,student.passwd,student.salt,class.classID,grade,classNum,major.majorID,majorName,course.courID,courTimeFrom,courTimeEnd,subject.subID,subName,teacher.tchID,tchName,tchGender,teacher.passwd,teacher.salt,faculty.facID,facName FROM attendance,student,class,major,course,subject,teacher,faculty where attendance.courID=course.courID AND attendance.stuID=student.stuID AND class.classID=student.classID AND class.majorID=major.majorID AND major.facID=faculty.facID AND course.subID=subject.subID AND course.tchID=teacher.tchID AND teacher.facID=faculty.facID AND attID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, attID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Subject subject = new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Teacher teacher = new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m") ? Gender.m : Gender.f, faculty, resultSet.getString("teacher.passwd"), resultSet.getString("teacher.salt"));
                        Course course = new Course(resultSet.getLong("courID"), subject, teacher, LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeFrom").getTime() / 1000, 0, ZoneOffset.of("+8")), LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeEnd").getTime() / 1000, 0, ZoneOffset.of("+8")));
                        Major major = new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), faculty);
                        AClass aClass = new AClass(resultSet.getLong("classID"), major, resultSet.getInt("grade"), resultSet.getInt("classNum"));
                        Student student = new Student(resultSet.getLong("stuID"), resultSet.getString("stuName"), resultSet.getString("stuGender").equals("m") ? Gender.m : Gender.f, aClass, resultSet.getString("student.passwd"), resultSet.getString("student.salt"));
                        return new Attendance(resultSet.getString("attID"), course, student, resultSet.getTimestamp("attTime") == null ? null : LocalDateTime.ofEpochSecond(resultSet.getTimestamp("attTime").getTime() / 1000, 0, ZoneOffset.of("+8")), AttStatus.fromString(resultSet.getString("attStatus")));
                    }
                }
            }
        }
        return null;
    }

    public static Attendance select(long stuID, long courID) throws SQLException, NoConfigException {
        String sql = "SELECT attID,attTime,attStatus,student.stuID,stuName,stuGender,student.passwd,student.salt,class.classID,grade,classNum,major.majorID,majorName,course.courID,courTimeFrom,courTimeEnd,subject.subID,subName,teacher.tchID,tchName,tchGender,teacher.passwd,teacher.salt,faculty.facID,facName FROM attendance,student,class,major,course,subject,teacher,faculty where attendance.courID=course.courID AND attendance.stuID=student.stuID AND class.classID=student.classID AND class.majorID=major.majorID AND major.facID=faculty.facID AND course.subID=subject.subID AND course.tchID=teacher.tchID AND teacher.facID=faculty.facID AND attendance.stuID=? AND attendance.courID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, stuID);
                preparedStatement.setLong(2, courID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Subject subject = new Subject(resultSet.getInt("subID"), resultSet.getString("subName"));
                        Faculty faculty = new Faculty(resultSet.getInt("facID"), resultSet.getString("facName"));
                        Teacher teacher = new Teacher(resultSet.getLong("tchID"), resultSet.getString("tchName"), resultSet.getString("tchGender").equals("m") ? Gender.m : Gender.f, faculty, resultSet.getString("teacher.passwd"), resultSet.getString("teacher.salt"));
                        Course course = new Course(resultSet.getLong("courID"), subject, teacher, LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeFrom").getTime() / 1000, 0, ZoneOffset.of("+8")), LocalDateTime.ofEpochSecond(resultSet.getTimestamp("courTimeEnd").getTime() / 1000, 0, ZoneOffset.of("+8")));
                        Major major = new Major(resultSet.getInt("majorID"), resultSet.getString("majorName"), faculty);
                        AClass aClass = new AClass(resultSet.getLong("classID"), major, resultSet.getInt("grade"), resultSet.getInt("classNum"));
                        Student student = new Student(resultSet.getLong("stuID"), resultSet.getString("stuName"), resultSet.getString("stuGender").equals("m") ? Gender.m : Gender.f, aClass, resultSet.getString("student.passwd"), resultSet.getString("student.salt"));
                        return new Attendance(resultSet.getString("attID"), course, student, resultSet.getTimestamp("attTime") == null ? null : LocalDateTime.ofEpochSecond(resultSet.getTimestamp("attTime").getTime() / 1000, 0, ZoneOffset.of("+8")), AttStatus.fromString(resultSet.getString("attStatus")));
                    }
                }
            }
        }
        return null;
    }

    public static Attendance insert(long courID, long stuID, @Nullable LocalDateTime attTime, AttStatus attStatus) throws NoConfigException, SQLException, AlreadyExistsException, DependenciesNotFoundException, InsertException {
        if (!CourseOA.isExists(courID) || !StudentOA.isExists(stuID)) throw new DependenciesNotFoundException();

        String sql = "INSERT INTO attendance VALUES (?,?,?,?,?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                UUID uuid;
                do {
                    uuid = UUID.randomUUID();
                } while (isExists(uuid.toString()));
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setLong(2, courID);
                preparedStatement.setLong(3, stuID);
                if (attTime == null) {
                    preparedStatement.setTimestamp(4, null);
                } else {
                    preparedStatement.setTimestamp(4, new Timestamp(attTime.toEpochSecond(ZoneOffset.of("+8")) * 1000));
                }
                preparedStatement.setString(5, attStatus.name());
                if (preparedStatement.executeUpdate() == 1) {
                    return new Attendance(uuid.toString(), CourseOA.select(courID), StudentOA.select(stuID), attTime, attStatus);
                } else {
                    throw new InsertException();
                }
            }
        }
    }

    public static boolean isExists(String attID) throws SQLException, NoConfigException {
        String sql = "SELECT EXISTS(SELECT 1 FROM attendance WHERE attID=?)";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, attID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean(1);
                    }
                }
            }
        }
        return false;
    }

    public static boolean delete(String attID) throws NoConfigException, SQLException {
        String sql = "DELETE FROM attendance WHERE attID=?";
        try (Connection connection = PoolHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, attID);
                return preparedStatement.executeUpdate() == 1;
            }
        }
    }
}
