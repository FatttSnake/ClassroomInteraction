package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.AttStatus;
import com.cfive.classroom.library.database.bean.Course;
import com.cfive.classroom.library.database.bean.Student;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.cfive.classroom.library.net.TeacherNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.event.ComponentAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private TeacherNet teacherNet;
    private String courseID;
    private final List<Student> studentList = new ArrayList<>();
    private static final Logger LOGGER= LogManager.getLogger();

    public Attendance() {
        //学生签到信息监听
        teacherNet.setOnReceiveListener(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                //判断该信息类型是否为签到并且签到状态是否为已签
                if(messageObject.getMessageType()== MessageType.CheckIn&&messageObject.getAttStatus()==AttStatus.signed){
                    try {
                        //将学生签到状态修改进数据表中
                        DatabaseHelper.updateAttendance(messageObject.getStuNo(), messageObject.getAttStatus());
                    } catch (NoConfigException e) {
                        JOptionPane.showMessageDialog(null,"没有数据库配置文件","警告",JOptionPane.ERROR_MESSAGE);
                        LOGGER.error("No configuration", e);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null,"数据库出错","警告",JOptionPane.ERROR_MESSAGE);
                        LOGGER.error("SQLException",e);
                    } catch (DependenciesNotFoundException e) {
                        LOGGER.error("DependenciesNotFoundException",e);
                    }
                }
            }
        });


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
    public  void start(TeacherNet teacherNet1,String courseID){
        frame.setContentPane(attendance.rootPanel);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        attendance.teacherNet=teacherNet1;
        attendance.courseID=courseID;
        try {
            studentList.addAll(DatabaseHelper.selectStudentsFromCourse(Long.parseLong(courseID)));  //导入该课程学生名单
        } catch (DependenciesNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoConfigException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //已签考勤表格
        DefaultTableModel alreadyTableModel=new DefaultTableModel(data,t1_columnTitle);
        alreadyTableModel.setColumnCount(3);
        attendance.table_already.setModel(alreadyTableModel);
        //未签考勤表格
        DefaultTableModel undoTableModel=new DefaultTableModel(data,t2_columnTitle);
        undoTableModel.setColumnCount(3);
        attendance.table_undo.setModel(undoTableModel);
        attendance.table_undo.setCellSelectionEnabled(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }

}
