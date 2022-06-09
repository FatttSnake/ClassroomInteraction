package com.cfive.classroom.teacher;

import javax.swing.*;

public class SendMessage {
    private  static final SendMessage sendMessage=new SendMessage();

    private static  JFrame frame = new JFrame("SendMessage");
    private JPanel rootPanel;
    private JTextPane textPane1;
    private JTextArea textArea1;
    private JButton bt_send;

    public static void main(String[] args) {
        frame.setContentPane(sendMessage.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }

    public  void start(){
        frame.setContentPane(sendMessage.rootPanel);
        frame.setBounds(600,400,600,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
