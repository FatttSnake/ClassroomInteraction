package com.cfive.classroom.student;

import javax.swing.*;

public class Chat {
    private JPanel panel1;
    private JTextField textField1;
    private JButton 发送Button;
    private JTextArea textArea1;
    static JFrame frame = new JFrame("test");
    public void start(){
        frame.setContentPane(new Chat().panel1);
        frame.setBounds(650,300,600,400);
        frame.setVisible(true);
    }
    public static void main(String[] args) {

        frame.setContentPane(new Chat().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setVisible(false);
    }
}
