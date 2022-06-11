package com.cfive.classroom.teacher;

import com.cfive.classroom.library.net.TeacherNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.text.SimpleDateFormat;

public class SendMessage {
    private  static final SendMessage sendMessage=new SendMessage();

    private static  JFrame frame = new JFrame("SendMessage");
    private JPanel rootPanel;
    private JTextArea messageInput;
    private JButton bt_sendMessage;
    private JTextArea messageShow;
    private TeacherNet teacherNet;

    public SendMessage() {
        //接收学生发过来的留言
        if(teacherNet!=null){
            teacherNet.setOnReceiveListener(new ReceiveListener() {
                @Override
                public void onReceive(MessageObject messageObject) {
                    if(messageObject.getMessageType()==MessageType.Chat){
                        SimpleDateFormat sendTime = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                        messageShow.append("学生 "+messageObject.getStuName()+": "+sendTime.format(System.currentTimeMillis())+'\n'+messageObject.getMessage());
                        System.out.println();
                    }

                }
            });
        }


       //发送按钮的监听
        bt_sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageToAll = messageInput.getText();
                if(messageToAll!=null){
                    SimpleDateFormat sendTime = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                    messageShow.append("@所有人:  "+sendTime.format(System.currentTimeMillis())+'\n'+messageToAll+'\n');
                    MessageObject messageObject = new MessageObject(null,null,null,"@所有人:"+messageToAll,null,null,MessageType.ChatToAll);
                    teacherNet.sendAllMessage(messageObject);
                    messageInput.setText("");
                }else {
                    JOptionPane.showMessageDialog(null,"无发送内容","错误！",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(sendMessage.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }

    public  void start(TeacherNet teacherNet1){
        frame.setContentPane(sendMessage.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        sendMessage.teacherNet = teacherNet1;
    }
}
