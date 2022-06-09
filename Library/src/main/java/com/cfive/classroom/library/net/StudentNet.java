package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class StudentNet {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Socket socket;

    public StudentNet(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    //发送
    public void sendMessageThread(MessageObject messageObject) {
        SendThread sendThread = new SendThread(socket, messageObject);
        sendThread.start();
    }

    //接受信息
    public void setOnReceiveListener(ReceiveListener receiveListener)
    {
        ReceiveThread receiveThread = new ReceiveThread(socket);
        receiveThread.setOnReceiveListener(receiveListener);
        receiveThread.start();
    }


}
