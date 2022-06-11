package com.cfive.classroom.teacher;

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
    private static final ChangePassword changePassword = new ChangePassword();
    private static JFrame frame = new JFrame("修改密码");
    private JPanel rootPanel;
    private JTextField workNo_input;
    private JButton cancel;
    private JButton confirm;
    private JPasswordField newPw_ok;
    private JPasswordField newPassword;
    private String workNo, password1, password2;
    private static final Logger LOGGER = LogManager.getLogger();


    public ChangePassword() {
        confirm.addActionListener(e -> {
            if (check()) {
                //将修改后的密码在数据表修改
                try {
                    DatabaseHelper.changePasswdInTeacher(Long.valueOf(workNo_input.getText()), String.valueOf(newPw_ok.getPassword()));
                    LOGGER.debug(String.valueOf(newPw_ok.getPassword()));
                } catch (NoConfigException ex) {
                    JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("No configuration", e);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "数据库出错", "警告", JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("SQLException", e);
                } catch (DependenciesNotFoundException ex) {
                    LOGGER.error("DependenciesNotFoundException", e);
                } catch (NoSuchAlgorithmException ex) {
                    LOGGER.error("NoSuchAlgorithmException", e);
                } catch (InvalidKeySpecException ex) {
                    LOGGER.error("InvalidKeySpecException", e);
                }
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
        frame.setSize(600, 400);
        frame.setVisible(false);
    }

    public void start(String workNo) {
        frame.setContentPane(changePassword.rootPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        changePassword.workNo = workNo;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean check() {
        password1 = String.valueOf(changePassword.newPw_ok.getPassword());
        password2 = String.valueOf(changePassword.newPassword.getPassword());
        if (String.valueOf(changePassword.workNo_input.getText()).equals(workNo)) {
            if (password1.length() == 0 || password2.length() == 0) {
                JOptionPane.showMessageDialog(null, "输入的密码为空");
                return false;
            } else if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(null, "两次输入密码不同");
                return false;
            } else return true;
        } else {
            JOptionPane.showMessageDialog(null, "请输入正确的工号");
            return false;
        }

    }
}
