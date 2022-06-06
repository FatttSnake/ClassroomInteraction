package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.util.PBKDF2Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class PBKDF2UtilTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private final PBKDF2Util pbkdf2Util = new PBKDF2Util();

    @Test
    void testPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "123456";

        String salt = pbkdf2Util.generateSalt();
        String pbkdf2 = pbkdf2Util.getEncryptedPassword(password, salt);
//        String md5 = md5Util.digest(password);

        LOGGER.debug("原始密码:" + password);
//        System.out.println("MD5加密后的密码:"+md5);
        LOGGER.debug("盐值:" + salt);
        LOGGER.debug("PBKDF2加盐后的密码:" + pbkdf2);
        LOGGER.debug("Test success");
    }
}