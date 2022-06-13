package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.Course;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

public class ClassList {
    private static final ClassList classList = new ClassList();
    private static JFrame frame = new JFrame("课堂互动通-教师端");
    private JPanel rootPanel1;
    private JButton bt_enter;
    private JComboBox comboBox;
    private JPanel selectPanel;
    private String workerNo, courseID, subName;
    private final List<Course> courseList = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public ClassList() {
        bt_enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info(workerNo);
                if (!Objects.equals(classList.comboBox.getSelectedItem(), "--请选择--")) {          //判断是否有选择内容
                    String select = classList.comboBox.getSelectedItem().toString();
                    courseID = select.substring(0, select.indexOf(" "));
                    subName = select.substring(select.indexOf(" ") + 1);
                    LOGGER.debug(courseID + " " + subName);
                    Main.start(workerNo, courseID, subName);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "请选择您想要进入的课程", "温馨提示！", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(classList.rootPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

    }

    public void start(String workerNo) {
        frame.setContentPane(classList.rootPanel1);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        classList.workerNo = workerNo;
        //添加下拉列表的内容：该教师所教的科目名以及其课程编号
        try {
            classList.comboBox.addItem("--请选择--");
            courseList.addAll(DatabaseHelper.queryCourses(Long.parseLong(classList.workerNo)));
            for (Course course : courseList) {
                classList.comboBox.addItem(course.getCourID() + " " + course.getSubject().getSubName());
            }
        } catch (NoConfigException e) {
            JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
            LOGGER.error("No configuration", e);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "数据库出错", "警告", JOptionPane.ERROR_MESSAGE);
            LOGGER.error("SQLException", e);
        } catch (DependenciesNotFoundException e) {
            LOGGER.error("DependenciesNotFoundException", e);
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
