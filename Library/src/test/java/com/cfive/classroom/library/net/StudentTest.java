package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class StudentTest {
    private static final Logger LOGGER = LogManager.getLogger();

    /*@Test
    void studentListenterTest()  {
        StudentNet studentNet = new StudentNet();
        try {
            studentNet.socketConnect("localhost",8888);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        studentNet.receiveMessageThread(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                LOGGER.info(messageObject.getCode());
            }
        });
        while(true);
    }


    @Test
    void teacherListenterTes() {
        TeacherNet teacherNet = new TeacherNet();
        try {
            teacherNet.socketConnect(8888);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageObject messageObject = new MessageObject();
        messageObject.setCode("123");
        teacherNet.sendMessageThread(messageObject);
        while(true);
    }*/
}
