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
    public static void main(String[] args) {
        FlatLightLaf.setup();

        frame.setContentPane(sigIn.rootJPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        sigIn.login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sigIn.workerNo=String.valueOf(sigIn.workerNo_input.getText());
                sigIn.password=String.valueOf(sigIn.password_input.getPassword());
                if(sigIn.workerNo.length()==0||sigIn.password.length()==0){         //判断用户名和密码是否为空
                    JOptionPane.showMessageDialog(null,"用户名和密码不能为空","提示！！",JOptionPane.ERROR_MESSAGE);
                }else{
                    try {
                        if(DatabaseHelper.checkPasswdInTeacher(Long.valueOf(sigIn.workerNo), sigIn.password)){
                            ClassList classList = new ClassList();
                            String test=sigIn.workerNo_input.getText().toString();
                            classList.start(test);
                            frame.setVisible(false);
                        }else {
                            JOptionPane.showMessageDialog(null,"密码错误，请重新输入","错误！！",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NoConfigException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (DependenciesNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    }


                }

            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
