package com.cfive.classroom.student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow{

    private JPanel rootPanel;
    private JLabel title;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton login;
    static JFrame frame = new JFrame("学生登录界面");
    public MainWindow() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    Center center = new Center();
                    center.start();
                    frame.setVisible(false);
                }
            }
        });

    }

    public boolean check(){
        String password = new String(passwordField1.getPassword());
        if (textField1.getText() == null || password.length() == 0) {
            JOptionPane.showMessageDialog(null, "账号密码不能为空");
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {

        frame.setContentPane(new MainWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setBounds(650,300,600,400);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
