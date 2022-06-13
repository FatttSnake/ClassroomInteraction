package com.cfive.classroom.library.net.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class SocketHandler extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Socket socket;
    private ReceiveListener receiveListener;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SocketHandler(Socket socket, ReceiveListener receiveListener) {
        this.socket = socket;
        this.receiveListener = receiveListener;
    }

    @Override
    public void run() {
        try (InputStream inputStream = this.socket.getInputStream()) {
            try (OutputStream outputStream = this.socket.getOutputStream()) {
                this.outputStream = outputStream;
                inputHandle(inputStream);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            try {
                this.socket.close();
            } catch (IOException ioException) {
                LOGGER.error(e);
            }
            this.interrupt();
        }

    }

    public void sendMessage(MessageObject message) {
        try {
            outputHandle(outputStream, message);
        } catch (Exception e) {
            LOGGER.error(e);
            try {
                this.socket.close();
            } catch (IOException ioException) {
                LOGGER.error(e);
            }
            this.interrupt();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void inputHandle(InputStream inputStream) throws IOException {
        if (objectOutputStream == null) {
            objectInputStream = new ObjectInputStream(inputStream);
        }
        while (true) {
            MessageObject message;
            try {
                message = (MessageObject) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                message = null;
            }
            if (receiveListener != null) {
                receiveListener.onReceive(message);
            }
        }
    }

    private void outputHandle(OutputStream outputStream, MessageObject message) throws IOException {
        if (objectOutputStream == null) {
            objectOutputStream= new ObjectOutputStream(outputStream);
        }
        objectOutputStream.writeObject(message);
    }

    public void setOnReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }
}
