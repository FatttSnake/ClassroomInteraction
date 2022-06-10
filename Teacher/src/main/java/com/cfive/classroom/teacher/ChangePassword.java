package com.cfive.classroom.teacher;

import javax.swing.*;

public class ChangePassword {
    private static final ChangePassword changePassword=new ChangePassword();
    private JPanel rootPanel;
    private JTextField workNo_input;
    private JButton cancel;
    private JButton confirm;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private  String workNo,password1,password2;

    static JFrame frame = new JFrame("ChangePassword");

    public ChangePassword() {
        confirm.addActionListener(e -> {
            if(check()){
                //将修改后的密码在数据表修改
                frame.dispose();
            }
        });
        cancel.addActionListener(e -> {
            frame.dispose();
        });
    }

    public static void main(String[] args) {

        frame.setContentPane(changePassword.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(false);
    }

    public void start(String workNo) {
        frame.setContentPane(changePassword.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        changePassword.workNo=workNo;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean check() {
        password1 = String.valueOf(changePassword.passwordField1.getPassword());
        password2 = String.valueOf(changePassword.passwordField2.getPassword());
        if(String.valueOf(changePassword.workNo_input.getText()).equals(workNo))
        {
            if (password1.length()==0 || password2.length()==0) {
                JOptionPane.showMessageDialog(null,"输入的密码为空");
                return false;
            } else if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(null, "两次输入密码不同");
                return false;
            } else return true;
        }else {
            JOptionPane.showMessageDialog(null, "请输入正确的工号");
            return false;
        }

    }
}
