package com.cfive.classroom.library.net.gzw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SendThread extends Thread{
    private static final Logger LOGGER = LogManager.getLogger();
    private Socket socket;
    private MessageObject messageObject;
    public SendThread(Socket socket,MessageObject messageObject) {
        this.socket = socket;
        this.messageObject = messageObject;
    }
    public void run() {
        try {

            // 获取输入的内容
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(messageObject);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
