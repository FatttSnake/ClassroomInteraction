package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import com.cfive.classroom.library.net.util.SocketHandler;
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
    private final ArrayList<SocketHandler> socketHandlers = new ArrayList<>();

    public TeacherNet(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketHandler socketHandler = new SocketHandler(socket, this.receiveListener);
                    socketHandler.start();
                    socketHandlers.add(socketHandler);
                } catch (IOException e) {
                    LOGGER.error("Could not accept connect", e);
                }
            }
        }).start();
    }

    public TeacherNet(int port, ReceiveListener receiveListener) throws IOException {
        this.receiveListener = receiveListener;
        serverSocket = new ServerSocket(port);
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketHandler socketHandler = new SocketHandler(socket, this.receiveListener);
                    socketHandler.start();
                    socketHandlers.add(socketHandler);
                } catch (IOException e) {
                    LOGGER.error("Could not accept connect", e);
                }
            }
        }).start();
    }

    public void sendAllMessage(MessageObject messageObject) {
        socketHandlers.forEach(socketHandler -> socketHandler.sendMessage(messageObject));
    }

    public void setOnReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
        socketHandlers.forEach(socketHandler -> socketHandler.setOnReceiveListener(receiveListener));
    }
}
