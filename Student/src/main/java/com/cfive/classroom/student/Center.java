package com.cfive.classroom.student;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.AttStatus;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.cfive.classroom.library.net.StudentNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class Center {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Center center = new Center();
    private JButton signInButton;
    private JButton raiseHandButton;
    private JButton chatButton;
    private JPanel rootpanel;
    private JButton changePasswordButton;
    private JTextArea textClass;
    private JButton connectButton;
    private JTextField stuNoText;
    private static JFrame frame = new JFrame("Center");
    private StudentNet studentNet;
    private String host;
    private int port;
    private String signInCode;
    private String stuNo;
    private String stuName;
    private MessageObject messageObject;

    public Center() {

        //留言
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chat chat = new Chat();
                chat.start(stuNo,stuName,studentNet);
            }
        });
        //修改密码
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword = new ChangePassword();
                changePassword.start(stuNoText.getText());
            }
        });
        //连接
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties properties = new Properties();
                try {
                    properties.load(new BufferedReader(new FileReader("Student/src/main/connect.properties")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                host = properties.getProperty("host");
                port = Integer.parseInt(properties.getProperty("port"));
                try {
                    studentNet = new StudentNet(host,port);
                    JOptionPane.showMessageDialog(null, "连接成功");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"连接失败");
                    LOGGER.error("IOException",ex);
                }
            }
        });
        //签到
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInCode = JOptionPane.showInputDialog(null,"签到码：","签到",JOptionPane.PLAIN_MESSAGE);
                studentNet.setOnReceiveListener(new ReceiveListener() {
                    @Override
                    public void onReceive(MessageObject messageObject) {
                        if (messageObject.getMessageType()==MessageType.CheckIn&&messageObject.getCode().equals(signInCode)) {
                            studentNet.sendMessageThread(new MessageObject(stuNo,stuName,null, null,null,AttStatus.signed,LocalDateTime.now(),null));
                            JOptionPane.showMessageDialog(null, "签到成功");
                        } else {
                            JOptionPane.showMessageDialog(null,"签到失败");
                        }
                    }
                });
            }
        });

        //举手
        raiseHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageObject = new MessageObject(stuNo, stuName, null, null, null, null,null,MessageType.RaiseHand);
                studentNet.sendMessageThread(messageObject);
                JOptionPane.showMessageDialog(null,"你已经向老师举手");
            }
        });
        //随机抽人
        studentNet.setOnReceiveListener(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                if (messageObject.getMessageType()==MessageType.Select) {
                    JOptionPane.showMessageDialog(null,"恭喜以下同学被选中:\n\t\n"+messageObject.getCount());
                }
            }
        });
    }


    public void start(String num){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(center.rootpanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        center.stuNoText.setText(num);
        stuNo = String.valueOf(stuNoText.getText());
        stuName = getName();
        LOGGER.info("学号"+stuNo);
        LOGGER.info("姓名"+stuName);
    }
    //获取学生姓名
    public String getName(){
        String name = null;
        try {
            name=DatabaseHelper.selectFromStudent(Long.parseLong(stuNoText.getText())).getStuName();
        } catch (NoConfigException e) {
            JOptionPane.showMessageDialog(null,"没有数据库配置文件","警告",JOptionPane.ERROR_MESSAGE);
            LOGGER.error("No configuration", e);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"数据库出错","警告",JOptionPane.ERROR_MESSAGE);
            LOGGER.error("SQLException",e);
        }
        return name;
    }


}
