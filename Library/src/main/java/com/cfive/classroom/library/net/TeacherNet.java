package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TeacherNet {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ServerSocket serverSocket;
    private ReceiveListener receiveListener;
    private final ArrayList<Socket> sockets = new ArrayList<>();


    public TeacherNet(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void waitForConnect() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ReceiveThread receiveThread = new ReceiveThread(socket);
                    if (receiveListener != null) {
                        receiveThread.setOnReceiveListener(receiveListener);
                    }
                    receiveThread.start();
                    sockets.add(socket);
                } catch (IOException e) {
                    LOGGER.error("Could not accept connect", e);
                }
            }
        }).start();
    }

    //群发
    public void sendAllMessage(MessageObject messageObject) {
        sockets.forEach(socket -> {
            SendThread sendThread = new SendThread(socket, messageObject);
            sendThread.start();
        });
    }

    //接受信息监听
    public void setOnReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }
}
