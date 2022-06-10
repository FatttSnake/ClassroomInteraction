package com.cfive.classroom.student;

import com.cfive.classroom.library.net.ReceiveThread;
import com.cfive.classroom.library.net.StudentNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class Chat {
    private static final Chat chat = new Chat();
    private JPanel panel1;
    private JButton sendButton;
    private JTextArea receiveText;
    private JTextArea sendText;
    private static JFrame frame = new JFrame("留言");
    private StudentNet studentNet;
    private String stuNo, stuName,host;
    private int port;

    public Chat() {

        //发送消息
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(String.valueOf(sendText.getText())!=null) {
                    studentNet.sendMessageThread(new MessageObject(stuNo, stuName, null, String.valueOf(sendText.getText()), null, MessageType.Chat));
                }
                else{
                    JOptionPane.showMessageDialog(null,"无发送内容","错误！",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //接收消息
        studentNet.setOnReceiveListener(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                if(messageObject.getMessageType()==MessageType.ChatToAll){
                    receiveText.setText("教师：\n"+messageObject.getMessage());
                }
            }
        });
    }
    public void start(String num, String name,StudentNet studentNet1) {
        frame.setContentPane(new Chat().panel1);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        chat.stuNo = num;
        chat.stuName = name;
        chat.studentNet = studentNet1;
    }
}
