package com.cfive.classroom.teacher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ClassList {
    private static final ClassList classlist=new ClassList();
    private static JFrame frame = new JFrame("课堂互动通-教师端");
    private JPanel rootPanel1;
    private JButton Button1;
    private JComboBox comboBox;
    private JPanel selectPanel;
    private String workerNo,className;
    public ClassList() {
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                className=(String) classlist.comboBox.getSelectedItem();
                Main.start(classlist.workerNo,classlist.className);
                frame.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(classlist.rootPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

    }
    public  void start(String workerNo){
        frame.setContentPane(classlist.rootPanel1);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        classlist.workerNo=workerNo;
        classlist.comboBox.addItem("20软工3班");
        classlist.comboBox.addItem("20软工4班");
        classlist.comboBox.addItem("20软工5班");
        classlist.comboBox.addItem("20软工6班");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
