package com.cfive.classroom.library.net.gzw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TeacherRecieveThread extends Thread{
    private static final Logger LOGGER = LogManager.getLogger();
    private Socket socket;
    private String stuNo;
    private String name;
    public TeacherRecieveThread(Socket socket) {
        this.socket = socket;

    }
    public void run() {
        try {
            // 接收对方输入的内容
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while (true) {
                stuNo = dataInputStream.readUTF();
                name = dataInputStream.readUTF();
                LOGGER.info(stuNo);
                LOGGER.info(name);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
