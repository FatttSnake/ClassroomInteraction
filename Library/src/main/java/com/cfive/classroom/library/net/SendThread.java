package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendThread extends Thread{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Socket socket;
    private final MessageObject messageObject;
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
            e.printStackTrace();
        } finally {
            interrupt();
        }
    }
}
