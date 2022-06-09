package com.cfive.classroom.student;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.Student;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.formdev.flatlaf.FlatLightLaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class MainWindow{
    private static final MainWindow mainWindow = new MainWindow();
    private JPanel rootPanel;
    private JLabel title;
    private JTextField stuNoText;
    private JPasswordField passwordText;
    private JButton login;
    private JLabel password;
    private static JFrame frame = new JFrame("学生登录界面");
    private static final Logger LOGGER = LogManager.getLogger();
    public MainWindow() {
        //登录按钮
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    Center center = new Center();
                    center.start(String.valueOf(stuNoText.getText()));
                    frame.dispose();
                }
            }
        });
    }
    public boolean check(){
        String stuPassword = new String(passwordText.getPassword());
        String stuNo = new String(stuNoText.getText());
        LOGGER.info(Long.valueOf(stuNo));
        //判断密码
        if (stuNo.length() == 0 || stuPassword.length() == 0) {
            JOptionPane.showMessageDialog(null, "账号密码不能为空");
            return false;
        } else {
            boolean checkPassword=false;
            try {
                checkPassword=DatabaseHelper.checkPasswdInStudent(Long.parseLong(stuNo),stuPassword);
            } catch (NoConfigException e) {
                JOptionPane.showMessageDialog(null,"没有数据库配置文件","警告",JOptionPane.ERROR_MESSAGE);
                LOGGER.error("No configuration", e);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"数据库出错","警告",JOptionPane.ERROR_MESSAGE);
                LOGGER.error("SQLException",e);
            } catch (DependenciesNotFoundException e) {
                LOGGER.error("DependenciesNotFoundException",e);
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("NoSuchAlgorithmException",e);
            } catch (InvalidKeySpecException e) {
                LOGGER.error("InvalidKeySpecException",e);
            }
            return checkPassword;
        }
    }
    public static void main(String[] args) {
        FlatLightLaf.setup();
        frame.setContentPane(mainWindow.rootPanel);
        frame.setSize(600,400);
        frame.setTitle("登录");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
