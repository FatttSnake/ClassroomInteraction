package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.Course;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.formdev.flatlaf.FlatLightLaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.NoRouteToHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public class SignIn {
    private static final SignIn sigIn = new SignIn();
    private static final JFrame frame = new JFrame("教师端");;
    private JPanel rootJPanel;
    private JButton login_Button;
    private JTextField workerNo_input;
    private JPasswordField password_input;
    private String workerNo,password;
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean isConnectedToDatabase = false;
    public static void main(String[] args) {
        FlatLightLaf.setup();

        frame.setContentPane(sigIn.rootJPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //回车键登录监听
        sigIn.password_input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    if(sigIn.isPwRight()){
                        ClassList classList = new ClassList();
                        classList.start(sigIn.workerNo);    //将工号传参到下一个界面
                        frame.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        //登录按钮监听
        sigIn.login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sigIn.isPwRight()){
                    ClassList classList = new ClassList();
                    classList.start(sigIn.workerNo);    //将工号传参到下一个界面
                    frame.dispose();
                }
            }
        });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private boolean isPwRight(){
        sigIn.workerNo=String.valueOf(sigIn.workerNo_input.getText());
        sigIn.password=String.valueOf(sigIn.password_input.getPassword());
        if(sigIn.workerNo.length()==0||sigIn.password.length()==0){         //判断用户名和密码是否为空
            JOptionPane.showMessageDialog(null,"用户名和密码不能为空","提示！！",JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            //根据输入的工号和密码利用加盐位进行判断
            try {
                if (DatabaseHelper.checkPasswdInTeacher(Long.valueOf(sigIn.workerNo), sigIn.password)) {
                    isConnectedToDatabase = true;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误，请重新输入", "错误！！", JOptionPane.ERROR_MESSAGE);
                    sigIn.password_input.setText("");   //清空输入框内容
                    isConnectedToDatabase = true;
                    return false;
                }
            } catch (NoConfigException ex) {
                JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
                LOGGER.error("No configuration", ex);
            } catch (SQLException ex) {
                LOGGER.error("SQLException", ex);
            } catch (DependenciesNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "未查询到该数据", "错误", JOptionPane.ERROR_MESSAGE);
                LOGGER.error("DependenciesNotFoundException", ex);
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("NoSuchAlgorithmException", ex);
            } catch (InvalidKeySpecException ex) {
                LOGGER.error("InvalidKeySpecException", ex);
            } finally {
                if (!isConnectedToDatabase) {
                    JOptionPane.showMessageDialog(null, "无法连接到数据库", "错误", JOptionPane.ERROR_MESSAGE);
                }
                isConnectedToDatabase = false;
            }
        }
        return false;
    }


}
