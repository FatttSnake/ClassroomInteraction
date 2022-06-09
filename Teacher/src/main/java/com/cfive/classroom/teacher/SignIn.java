package com.cfive.classroom.teacher;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn {
    private static final SignIn sigIn = new SignIn();
    private static final JFrame frame = new JFrame("教师端");;
    private JPanel rootJPanel;
    private JButton login_Button;
    private JTextField workerNo;
    private JPasswordField password;

    public static void main(String[] args) {
        FlatLightLaf.setup();

        frame.setContentPane(sigIn.rootJPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        sigIn.login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!sigIn.workerNo.getText().isEmpty()&&!sigIn.password.getText().isEmpty()){
                    ClassList classList = new ClassList();
                    String test=sigIn.workerNo.getText().toString();
                    classList.start(test);
                    frame.setVisible(false);
                }else{

                    JOptionPane.showMessageDialog(null,"用户名和密码不能为空","提示！！",JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
