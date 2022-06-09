package com.cfive.classroom.teacher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main {
    private static final Main main=new Main();
    private static  JFrame frame = new JFrame("课堂互动通");
    private JPanel rootPanel;
    private JButton bt_checkIn;
    private JButton bt_attendance;
    private JButton bt_select;
    private JButton bt_sendMessage;

    private JButton changePasswordButton;
    private JTextField workNo;
    private JTextField className;

    public Main() {
        bt_sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage sendMessage=new SendMessage();
                sendMessage.start();
            }
        });
        bt_checkIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckIn checkIn=new CheckIn();
                checkIn.start();
            }
        });
        bt_attendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Attendance attendance=new Attendance();
                attendance.start();
            }
        });
        bt_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int person;
                String count;
                Object[] possibleValues = {"提问1个同学", "提问2个同学", "提问3个同学", "提问4个同学", "提问5个同学", "提问6个同学", "提问7个同学"};
                Object selectedValue = JOptionPane.showInputDialog(null, "选择提问同学个数", "随机选人，持续工作中...",
                        JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
                while (true) {
                    count = "";
                    String substring = selectedValue.toString().substring(2, 3);
                    int i = Integer.parseInt(substring);
                    String[] arr = new String[i];
                    for (int j = 0; j < i; ) {
                        //随机得到的数值
                        person = (int) (Math.random() * 10);
                        //遍历数组对
                        if (!Arrays.asList(arr).contains(String.valueOf(person))) {
                            arr[j] = String.valueOf(person);
                            j++;
                        }
                    }
                    String[] arr2 = {"小白 ", "小红 ", "小黑 ", "小绿 ", "小宗 ", "小橙 ", "小黄 ", "小青 ", "小蓝 ", "小紫 ", };
                    for (int s = 0; s < arr.length; s++) {
                        person = Integer.parseInt(arr[s]);
                        count += arr2[person];
                    }
                    JOptionPane.showMessageDialog(null, "恭喜以下同学，获得了本次回答问题的机会\n\t\n" + count);
                    break;
                }
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword=new ChangePassword();
                changePassword.start();
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(main.rootPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }

    public static void start(String workerNo,String className){
        frame.setContentPane(main.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        main.workNo.setText(workerNo);
        main.className.setText(className);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
