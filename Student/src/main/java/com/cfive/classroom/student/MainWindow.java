package com.cfive.classroom.student;

import com.cfive.classroom.library.database.Test;

import javax.swing.*;

public class MainWindow {

    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        com.cfive.classroom.library.net.Test.run();
        Test.run();
    }
}
