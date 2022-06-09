package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ReceiveThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();
    private Socket socket;
    private ReceiveListener receiveListener;
    private MessageObject messageObject;
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

                messageObject = (MessageObject) objectInputStreamInputStream.readObject();
//                LOGGER.info(messageObject);
                if (receiveListener != null) {
                    receiveListener.onReceive(messageObject);
                }
//                System.out.println(msg);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




}
