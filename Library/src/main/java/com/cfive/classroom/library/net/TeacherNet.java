package com.cfive.classroom.library.net;

import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TeacherNet {
    private static final Logger LOGGER = LogManager.getLogger();
    private ServerSocket serverSocket;
    private Socket socket;
    private MessageObject messageObject = new MessageObject();;
/*
    public static void main(String[] args) {
        TeacherNet teacher = new TeacherNet();
        try {
            teacher.socketConnect();
            teacher.sendThreadStart();
//            teacher.teacherReceive();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
*/


    public void socketConnect(int port) throws IOException
    {// 监听端口
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
    }


    //发送
    public void sendMessageThread(MessageObject messageObject) {
        SendThread sendThread = new SendThread(socket, messageObject);
        sendThread.start();
        //打斷線程
        sendThread.interrupt();
    }


    //接受信息
    public void receiveMessageThread(ReceiveListener receiveListener)
    {
        ReceiveThread receiveThread = new ReceiveThread(socket);
        receiveThread.setOnReceiveListener(receiveListener);
        receiveThread.start();
    }



}
