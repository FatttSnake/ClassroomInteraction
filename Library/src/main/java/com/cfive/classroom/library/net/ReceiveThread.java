package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ReceiveThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Socket socket;
    private ReceiveListener receiveListener;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }
    public void setOnReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }
    public void run() {
        try {
            // 接收对方输入的内容
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStreamInputStream = new ObjectInputStream(inputStream);

            while (true) {

                MessageObject messageObject = (MessageObject) objectInputStreamInputStream.readObject();
                if (receiveListener != null) {
                    receiveListener.onReceive(messageObject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




}
