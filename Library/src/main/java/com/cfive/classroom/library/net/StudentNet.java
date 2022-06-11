package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import com.cfive.classroom.library.net.util.SocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class StudentNet {
    private static final Logger LOGGER = LogManager.getLogger();
    private final SocketHandler socketHandler;

    public StudentNet(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        socketHandler = new SocketHandler(socket, null);
        socketHandler.start();
    }

    public StudentNet(String host, int port, ReceiveListener receiveListener) throws IOException {
        Socket socket = new Socket(host, port);
        socketHandler = new SocketHandler(socket, receiveListener);
        socketHandler.start();
    }

    public void setOnReceiveListener(ReceiveListener receiveListener) {
        socketHandler.setOnReceiveListener(receiveListener);
    }

    //发送
    public void sendMessage(MessageObject messageObject) {
        socketHandler.sendMessage(messageObject);
    }

}
