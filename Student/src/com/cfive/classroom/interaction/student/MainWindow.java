package com.cfive.classroom.interaction.student;

import com.cfive.classroom.interaction.library.database.Test;

import javax.swing.*;

public class MainWindow {

    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        com.cfive.classroom.interaction.library.net.Test.run();
        Test.run();
    }
}
