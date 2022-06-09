package com.cfive.classroom.library.net.gzw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class StudentNet {
    private static final Logger LOGGER = LogManager.getLogger();
    private Socket socket;
    private MessageObject messageObject = new MessageObject();

/*
    public StudentNet(String host, int port, ReceiveListener receiveListener) throws IOException {
        socketConnect(host, port);
        receiveMessageThread(receiveListener);
    }
*/
    public StudentNet(){}

    public void socketConnect(String host, int port) throws UnknownHostException, IOException
    {
        socket = new Socket(host, port);
    }

    //发送
    public void sendMessageThread(MessageObject messageObject) {
        SendThread sendThread = new SendThread(socket, messageObject);
        sendThread.start();
    }

    //接受信息
    public void receiveMessageThread(ReceiveListener receiveListener)
    {
        RecieveThread recieveThread = new RecieveThread(socket);
        recieveThread.setOnReceiveListener(receiveListener);
        recieveThread.start();
    }


}
