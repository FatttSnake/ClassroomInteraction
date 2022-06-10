package com.cfive.classroom.teacher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CheckIn {
    private static final CheckIn checkIn=new CheckIn();
    private static final JFrame frame= new JFrame("发布签到码");
    private JPanel rootPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton bt_confim;
    private JButton bt_cancel;
    private String n1,n2,n3,n4,number;
    private static final Logger LOGGER= LogManager.getLogger();

    public CheckIn() {
        //限制签到码的长度
        bt_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                n1=textField1.getText();
                if(n1.length()>=1){
                    e.consume();
                }
            }
        });
        textField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                n2=textField2.getText();
                if(n2.length()>=1){
                    e.consume();
                }
            }
        });
        textField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                n3=textField3.getText();
                if(n3.length()>=1){
                    e.consume();
                }
            }
        });textField4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                n4=textField4.getText();
                if(n4.length()>=1){
                    e.consume();
                }
            }
        });
        //确定按钮的点击事件
        bt_confim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                number=n1+n2+n3+n4;
                LOGGER.info(number);
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(checkIn.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }
    public  void start(){
        frame.setContentPane(checkIn.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
