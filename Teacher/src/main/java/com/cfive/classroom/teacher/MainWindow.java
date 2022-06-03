package com.cfive.classroom.teacher;

import com.cfive.classroom.library.net.Test;

import javax.swing.*;

public class MainWindow {

    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Test.run();
        com.cfive.classroom.library.database.Test.run();

        System.out.println("This is a test");
    }
}