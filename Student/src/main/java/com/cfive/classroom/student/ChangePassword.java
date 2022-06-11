package com.cfive.classroom.student;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class ChangePassword {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ChangePassword changePassword = new ChangePassword();

    private JPanel rootPanel;
    private JTextField textNum;
    private JButton cancel;
    private JButton confirm;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private static JFrame frame = new JFrame("修改密码");

    private String stuNo;

    public ChangePassword() {
        confirm.addActionListener(e -> {
            if(check()){
                try {
                    DatabaseHelper.changePasswdInStudent(Long.parseLong(stuNo), String.valueOf(passwordField1.getPassword()));
                } catch (NoConfigException ex) {
                    JOptionPane.showMessageDialog(null,"没有数据库配置文件","警告",JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("No configuration", e);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"数据库出错","警告",JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("SQLException",e);
                } catch (DependenciesNotFoundException ex) {
                    LOGGER.error("DependenciesNotFoundException",e);
                } catch (NoSuchAlgorithmException ex) {
                    LOGGER.error("NoSuchAlgorithmException",e);
                } catch (InvalidKeySpecException ex) {
                    LOGGER.error("InvalidKeySpecException",e);
                }
                frame.dispose();
            }
        });
        cancel.addActionListener(e -> {
            frame.dispose();
        });
    }



    private boolean check() {
        String password1,password2,num;
        num = String.valueOf(textNum.getText());

        LOGGER.info("传入学号"+stuNo);
        LOGGER.info(num);

        password1 = String.valueOf(passwordField1.getPassword());
        password2 = String.valueOf(passwordField2.getPassword());
        if(num.equals(stuNo)){
            if (password1.length()==0 || password2.length()==0) {
                JOptionPane.showMessageDialog(null,"输入的密码为空");
                return false;
            } else if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(null, "两次输入密码不同");
                return false;
            } else return true;
        }
        else {
            JOptionPane.showMessageDialog(null,"学号非本人学号");
            return false;
        }
    }


    public void start(String stuNo) {
        frame.setContentPane(changePassword.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        changePassword.stuNo = stuNo;
        LOGGER.info(changePassword.stuNo);
    }
    public static void main(String[] args) {
        frame.setContentPane(changePassword.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(false);
    }
}
