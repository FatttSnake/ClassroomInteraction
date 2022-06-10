package com.cfive.classroom.teacher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.event.ComponentAdapter;

public class Attendance {
    private static final Attendance attendance=new Attendance();
    private static final JFrame frame = new JFrame("考勤情况");
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private Object[] t1_columnTitle = {"学号" , "姓名" , "签到时间"};
    private Object[] t2_columnTitle={"学号","姓名","签到状态"};
    private Object[][] data={{"a","b","c"},{"d","e","f"},{"1","2","3"}};
    private JTable table_already;
    private JTable table_undo;
    private JLabel test;
    private static final Logger LOGGER= LogManager.getLogger();

    public Attendance() {


        table_undo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table_undo.getSelectedRow();
                // int col = table_undo.getSelectedColumn();
                String newString=table_undo.getValueAt(row,2).toString();
                attendance.test.setText(newString);
                LOGGER.info(newString);




            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(attendance.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

    }
    public  void start(){
        frame.setContentPane(attendance.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        DefaultTableModel alreadyTableModel=new DefaultTableModel(data,t1_columnTitle);
        alreadyTableModel.setColumnCount(3);
        attendance.table_already.setModel(alreadyTableModel);
        DefaultTableModel undoTableModel=new DefaultTableModel(data,t2_columnTitle);
        undoTableModel.setColumnCount(3);
        attendance.table_undo.setModel(undoTableModel);
        attendance.table_undo.setCellSelectionEnabled(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }

}
