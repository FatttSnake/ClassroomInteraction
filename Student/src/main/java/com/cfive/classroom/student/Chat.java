package com.cfive.classroom.student;

import com.cfive.classroom.library.net.StudentNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Chat {
    private JPanel panel1;
    private JButton sendButton;
    private JTextArea receiveText;
    private JTextArea sendText;
    private static JFrame frame = new JFrame("留言");
    private static final Logger LOGGER = LogManager.getLogger();

    public Chat(StudentNet studentNet,String stuNo,String stuName,ChatReceiveListener chatReceiveListener) {
        //发送消息
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sendMessage = sendText.getText();
                if(sendMessage!=null) {
                    LOGGER.info(stuNo+stuName);
                    receiveText.append("我："+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒"))+"\n"+sendMessage+"\n");
                    studentNet.sendMessage(new MessageObject(stuNo, stuName, null,sendText.getText() ,null,null,LocalDateTime.now(),MessageType.Chat));
                    sendText.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null,"无发送内容","错误！",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //接收消息
        studentNet.setOnReceiveListener(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                if(messageObject.getMessageType()==MessageType.ChatToAll){
                    receiveText.append("教师："+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒"))+'\n'+messageObject.getMessage()+"\n");
                }
                chatReceiveListener.onReceive(messageObject);
            }
        });
    }

    public void start() {
        frame.setContentPane(this.panel1);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
