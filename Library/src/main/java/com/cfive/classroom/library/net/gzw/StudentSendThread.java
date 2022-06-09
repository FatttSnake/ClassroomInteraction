package com.cfive.classroom.library.net.gzw;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class StudentSendThread extends  Thread {
    private Socket socket;
    private String stuNo;
    private String name;
    public StudentSendThread(Socket socket,String stuNo,String name) {
        this.socket = socket;
        this.stuNo=stuNo;
        this.name=name;
    }
    public void run() {
        try {

            // 获取输入的内容
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
/*

            while (true) {

                dataOutputStream.writeUTF(stuNo+"\t"+name);
                dataOutputStream.writeUTF(stuNo);
                dataOutputStream.writeUTF(name);
            }
*/
            dataOutputStream.writeUTF(stuNo);
            dataOutputStream.writeUTF(name);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
