package com.cfive.classroom.student;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.AttStatus;
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
    private static Center center;
    private JButton raiseHandButton;
    private JButton chatButton;
    private JPanel rootpanel;
    private JButton changePasswordButton;
    private JButton connectButton;
    private JTextField stuNoText;
    private JTextField stuNameText;
    private JButton signInButton;
    private static JFrame frame = new JFrame("Center");
    private StudentNet studentNet;
    private String host;
    private int port;
    private String signInCode;
    private String stuNo;
    private String stuName;
    private String getSignInCode;
    private MessageObject messageObject;

    public Center(String stuNo) {
        this.stuNo = stuNo;
        this.stuName = getName(stuNo);
        stuNoText.setText(stuNo);
        stuNameText.setText(stuName);
        //留言
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info("chatButton.studentNet"+studentNet);
                if (studentNet != null) {
                    Chat chat = new Chat(studentNet,stuNo,stuName);
                    chat.start();
                } else {
                    JOptionPane.showMessageDialog(null,"没有连接至教师");
                }
            }
        });
        //修改密码
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword = new ChangePassword(stuNoText.getText());
                changePassword.start();
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
                    //签到
                    studentNet.setOnReceiveListener(new ReceiveListener() {
                        @Override
                        public void onReceive(MessageObject messageObject) {
                            if (messageObject.getMessageType() == MessageType.CheckIn) {
                                getSignInCode = messageObject.getCode();
                                LOGGER.info(messageObject.getCode());
                            }
                        }
                    });

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"连接失败");
                    LOGGER.error("IOException",ex);
                }
                LOGGER.info("connect.studentNet"+studentNet);
            }
        });


        //举手
        raiseHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LOGGER.info(stuNo+stuName);
                messageObject = new MessageObject(stuNo, stuName, null, null, null, null,null,MessageType.RaiseHand);
                studentNet.sendMessage(messageObject);
                LOGGER.info(messageObject.getStuNo()+messageObject.getStuName());
                JOptionPane.showMessageDialog(null,"你已经向老师举手");
            }
        });
        //随机抽人
        if (studentNet != null) {
            studentNet.setOnReceiveListener(new ReceiveListener() {
                @Override
                public void onReceive(MessageObject messageObject) {
                    if (messageObject.getMessageType()==MessageType.Select) {
                        JOptionPane.showMessageDialog(null,"恭喜以下同学被选中:\n\t\n"+messageObject.getCount());
                    }
                }
            });
        }
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSignInCode != null) {
                    signInCode = JOptionPane.showInputDialog(null, "签到码：", "签到", JOptionPane.PLAIN_MESSAGE);
                    LOGGER.info(getSignInCode);
                    if (getSignInCode.equals(signInCode)) {
                        studentNet.sendMessage(new MessageObject(stuNo, stuName, null, null, null, AttStatus.signed, LocalDateTime.now(), MessageType.CheckIn));
                        JOptionPane.showMessageDialog(null, "签到成功");
                    } else {
                        JOptionPane.showMessageDialog(null, "签到失败");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"无签到码");
                }
            }
        });
    }


    public void start(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(rootpanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(center.rootpanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }
    //获取学生姓名
    public String getName(String stuNo){
        String name = null;
        try {
            name=DatabaseHelper.selectFromStudent(Long.parseLong(stuNo)).getStuName();
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
