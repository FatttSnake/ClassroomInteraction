package com.cfive.classroom.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MyTest {
    private static final Logger LOGGER = LogManager.getLogger();
    @Test
    void firstTest() {
        LOGGER.info("This is a log");

        try {
            throw new Exception("exception");
        } catch (Exception e) {
            LOGGER.error("Err", e);
        }
    }

    @Test
    void localDateTimeTest() {
        LOGGER.info(LocalDateTime.now());
    }
}
