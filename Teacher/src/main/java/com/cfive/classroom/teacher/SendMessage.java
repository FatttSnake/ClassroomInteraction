package com.cfive.classroom.teacher;

import com.cfive.classroom.library.net.TeacherNet;

import javax.swing.*;

public class SendMessage {
    private  static final SendMessage sendMessage=new SendMessage();

    private static  JFrame frame = new JFrame("SendMessage");
    private JPanel rootPanel;
    private JTextArea messageInput;
    private JButton bt_send;
    private JTextArea messageShow;
    private TeacherNet teacherNet;

    public static void main(String[] args) {
        frame.setContentPane(sendMessage.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }

    public  void start(TeacherNet teacherNet1){
        frame.setContentPane(sendMessage.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        sendMessage.teacherNet = teacherNet1;
    }
}
