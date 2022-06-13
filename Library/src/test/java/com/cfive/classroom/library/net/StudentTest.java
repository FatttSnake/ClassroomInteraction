package com.cfive.classroom.library.net;

import com.cfive.classroom.library.database.bean.AttStatus;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class StudentTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    void teacherTest() throws IOException {
        TeacherNet teacherNet = new TeacherNet(8888);
        teacherNet.setOnReceiveListener(messageObject -> {
            LOGGER.debug(messageObject);
            teacherNet.sendAllMessage(messageObject);
        });

        while (true);
    }

    @Test
    void studentTest() throws IOException {
        StudentNet studentNet = new StudentNet("localhost", 8888);
        studentNet.setOnReceiveListener(LOGGER::debug);
        studentNet.sendMessage(new MessageObject("stuNO", "stuName", "code", "message", "count", AttStatus.not_signed, LocalDateTime.now(), MessageType.Chat));
        while (true);
    }

    @Test
    void studentTest1() throws IOException {
        StudentNet studentNet = new StudentNet("localhost", 8888);
        studentNet.setOnReceiveListener(LOGGER::debug);
        studentNet.sendMessage(new MessageObject("stuNO", "stuName", "code", "message", "count", AttStatus.not_signed, LocalDateTime.now(), MessageType.Chat));
        while (true);
    }

    @Test
    void studentTest2() throws IOException {
        StudentNet studentNet = new StudentNet("localhost", 8888);
        studentNet.setOnReceiveListener(LOGGER::debug);
        studentNet.sendMessage(new MessageObject("stuNO", "stuName", "code", "message", "count", AttStatus.not_signed, LocalDateTime.now(), MessageType.Chat));
        studentNet.sendMessage(new MessageObject("stuNO", "stuName", "code", "message", "count", AttStatus.not_signed, LocalDateTime.now(), MessageType.Chat));
        while (true);
    }
}
