package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class TeacherTest {
    private static final Logger LOGGER = LogManager.getLogger();
    @Test
    void modifyPw(){
        try {
            DatabaseHelper.changePasswdInTeacher(Long.valueOf("1004"),"10041004");
        } catch (NoConfigException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DependenciesNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
