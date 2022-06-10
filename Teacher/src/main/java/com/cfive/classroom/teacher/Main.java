package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.Course;
import com.cfive.classroom.library.database.bean.Student;
import com.cfive.classroom.library.database.bean.Subject;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.cfive.classroom.library.net.TeacherNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final Main main = new Main();
    private static JFrame frame = new JFrame("课堂互动通");
    private JPanel rootPanel;
    private JButton bt_checkIn;
    private JButton bt_attendance;
    private JButton bt_select;
    private JButton bt_sendMessage;

    private JButton changePasswordButton;
    private JTextField workNo_show;
    private JTextField subName_show;
    private String workNo,courseID;
    private List<Student> studentList;
    private String[] student;
    private TeacherNet teacherNet;
    private final Logger LOGGER = LogManager.getLogger();

    public Main() {

        //从配置文件中读取端口号并传参到主界面
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("Teacher/src/main/connect.properties"));
        } catch (IOException ex) {
            LOGGER.error("IOException", ex);
        }
        LOGGER.debug(properties.getProperty("port"));
        try {
            if (!properties.getProperty("port").isEmpty()) {
                teacherNet = new TeacherNet(Integer.valueOf(properties.getProperty("port")));
            }
        } catch (IOException ex) {
            LOGGER.error("IOException", ex);
        }

        //发送消息按钮的监听
        bt_sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.start(teacherNet);
            }
        });

        //发布签到按钮的监听
        bt_checkIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckIn checkIn = new CheckIn();
                checkIn.start(teacherNet);
            }
        });

        // 考勤情况按钮的监听
        bt_attendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Attendance attendance = new Attendance();
                attendance.start(teacherNet);
            }
        });

        //随机选人按钮的监听
        bt_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int person;
                String count="";
                Object[] dropList = {"提问1个同学", "提问2个同学", "提问3个同学", "提问4个同学", "提问5个同学", "提问6个同学", "提问7个同学"};
                Object selectedValue = JOptionPane.showInputDialog(null, "选择提问同学个数", "随机选人，持续工作中...",
                        JOptionPane.INFORMATION_MESSAGE, null, dropList, dropList[0]);  //下拉列表的内容：选择提问的人数
                String substring = selectedValue.toString().substring(2, 3);    //获取所选择的人数转化成int类型
                int i = Integer.parseInt(substring);
                String[] arr = new String[i];   //new一个该数长度的String数组

                try {
                    studentList = DatabaseHelper.selectStudentsFromCourse(Long.parseLong(courseID));
                    if (!studentList.isEmpty()) {       //判断是否获取到学生名单
                        LOGGER.info("学生列表" + studentList);
                        for (int j = 0; j < i; ) {
                            //随机得到的数值
                            person = (int) (Math.random() * studentList.size());
                            LOGGER.info("随机值：" + person);
                            //将该数组转化为列表，并看该列表是否包含该随机数，没有则添加进去(确保随机数不重复)
                            if (!Arrays.asList(arr).contains(String.valueOf(person))) {
                                arr[j] = String.valueOf(person);
                                j++;
                            }
                        }
                        Iterator<Student> iterator = studentList.iterator();
                        while (iterator.hasNext()) {
                            student = new String[studentList.size()];
                            for (int k = 0; k < studentList.size(); k++) {
                                student[k] = iterator.next().getStuName();
                            }

                        }
                        for (int s = 0; s < arr.length; s++) {  //遍历该数组并把每一个随机数所对应的人放到count中
                            person = Integer.parseInt(arr[s]);
                            count += student[person];
                            count+="  ";
                        }
                        JOptionPane.showMessageDialog(null, "恭喜以下同学被选中：\n\t\n" + count);
                        //将选人结果群发出去
                        teacherNet.sendAllMessage(new MessageObject(null, null, null, null, count, MessageType.Select));
                    }else {
                        JOptionPane.showMessageDialog(null, "学生名单未导入", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (DependenciesNotFoundException ex) {
                    LOGGER.error("DependenciesNotFoundException", e);
                } catch (NoConfigException ex) {
                    JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("No configuration", e);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "数据库出错", "警告", JOptionPane.ERROR_MESSAGE);
                    LOGGER.error("SQLException", e);

                }

            }
        });

        //修改密码按钮的监听
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword changePassword = new ChangePassword();
                changePassword.start(main.workNo);
            }
        });

        //主界面线程监听
        teacherNet.setOnReceiveListener(new ReceiveListener() {
            @Override
            public void onReceive(MessageObject messageObject) {
                //学生端举手监听
                if (messageObject.getMessageType() == MessageType.RaiseHand) {
                    JOptionPane.showMessageDialog(null, messageObject.getStuName() + " 举手了", "温馨提示！", JOptionPane.INFORMATION_MESSAGE);
                }
                //学生留言监听
                if (messageObject.getMessageType() == MessageType.Chat) {
                    JOptionPane.showMessageDialog(null, messageObject.getMessage(), "学生 " + messageObject.getStuName() + " 向您留言", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(main.rootPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }

    public static void start(String workerNo,String courseID,String subName) {
        frame.setContentPane(main.rootPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        main.workNo = workerNo;
        main.courseID = courseID;
        //开启主界面即读取端口号
        main.workNo_show.setText(workerNo);
        main.subName_show.setText(subName);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
