package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.Course;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.cfive.classroom.library.net.TeacherNet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ClassList {
    private static final ClassList classlist=new ClassList();
    private static JFrame frame = new JFrame("课堂互动通-教师端");
    private JPanel rootPanel1;
    private JButton Button1;
    private JComboBox comboBox;
    private JPanel selectPanel;
    private String workerNo, subName;
    private List<Course> courseList;
    private static final Logger LOGGER = LogManager.getLogger();
    private TeacherNet teacherNet;
    public ClassList() {
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subName =(String) classlist.comboBox.getSelectedItem();
                //从配置文件中读取端口号并传参到主界面
                Properties properties = new Properties();
                try {
                    properties.load(new BufferedReader(new FileReader("Teacher/src/main/connect.properties")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                LOGGER.info(properties.getProperty("port"));
                try {
                    teacherNet = new TeacherNet(Integer.valueOf(properties.getProperty("port")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(subName!=null){
                    Main.start(classlist.workerNo,classlist.subName,teacherNet);
                }
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
        //添加下拉列表的内容：该教师所教的科目名
        try {
            courseList=DatabaseHelper.queryCourses(Long.valueOf(classlist.workerNo));
            if(courseList!=null){
                Iterator<Course> iterator = courseList.iterator();  //使用迭代器进行遍历
                while(iterator.hasNext()){
                    String subName = iterator.next().getSubject().getSubName();
                    classlist.comboBox.addItem(subName);
                }
            }
        } catch (NoConfigException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DependenciesNotFoundException e) {
            throw new RuntimeException(e);
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
