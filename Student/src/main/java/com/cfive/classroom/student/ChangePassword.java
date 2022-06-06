package com.cfive.classroom.student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton cancel;
    private JButton confirm;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    static JFrame frame = new JFrame("ChangePassword");

    public ChangePassword() {
        confirm.addActionListener(e -> {
            if(check()){
                Center.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        cancel.addActionListener(e -> {
            Center.frame.setVisible(true);
            frame.setVisible(false);
        });
    }

    public static void main(String[] args) {

        frame.setContentPane(new ChangePassword().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(false);
    }

    public void start() {
        frame.setContentPane(new ChangePassword().rootPanel);
        frame.setBounds(650,300,600,400);
        frame.setResizable(false);
//        frame.pack();
        frame.setVisible(true);
    }

    private boolean check() {
        String password1,password2;
        password1 = String.valueOf(passwordField1.getPassword());
        password2 = String.valueOf(passwordField2.getPassword());
        if (password1.length()==0 || password2.length()==0) {
            JOptionPane.showMessageDialog(null,"输入的密码为空");
            return false;
        } else if (password1.equals(password2) == false) {
            JOptionPane.showMessageDialog(null, "两次输入密码不同");
            return false;
        } else return true;
    }
}
