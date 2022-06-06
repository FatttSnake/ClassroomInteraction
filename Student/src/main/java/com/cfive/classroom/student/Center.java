package com.cfive.classroom.student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Center {
    private JButton Button1;
    private JButton button2;
    private JButton chatButton;
    private JPanel rootpanel2;
    private JTextField textField1;
    private JButton changePasswordButton;
    private JTextArea textArea1;
    private JButton 连接Button;
    static JFrame frame = new JFrame("Center");

    public Center() {
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chat chat = new Chat();
                chat.start();
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword = new ChangePassword();
                changePassword.start();
                frame.setVisible(false);
            }
        });
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = JOptionPane.showInputDialog(null,"签到码：","签到",JOptionPane.PLAIN_MESSAGE);
                if (code.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "签到成功");
                } else {
                    JOptionPane.showMessageDialog(null,"签到失败");
                }
            }
        });
    }

    public void start(){
        frame.setContentPane(new Center().rootpanel2);
        frame.setBounds(650,300,600,400);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        frame.setContentPane(new Center().rootpanel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setResizable(false);
        frame.setVisible(false);
    }
}
