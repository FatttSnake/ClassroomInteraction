package com.cfive.classroom.teacher;

import com.cfive.classroom.library.database.DatabaseHelper;
import com.cfive.classroom.library.database.bean.AttStatus;
import com.cfive.classroom.library.database.bean.Attendance;
import com.cfive.classroom.library.database.util.DependenciesNotFoundException;
import com.cfive.classroom.library.database.util.NoConfigException;
import com.cfive.classroom.library.net.TeacherNet;
import com.cfive.classroom.library.net.util.MessageObject;
import com.cfive.classroom.library.net.util.MessageType;
import com.cfive.classroom.library.net.util.ReceiveListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Attend {
    private static final Attend attend = new Attend();
    private static final JFrame frame = new JFrame("考勤情况");
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private final Object[] t1_columnTitle = {"学号", "姓名", "签到时间"};
    private final Object[] t2_columnTitle = {"考勤号", "学号", "姓名", "签到状态"};
    private JTable table_already;
    private JTable table_undo;
    private JButton bt_refresh;
    private TeacherNet teacherNet;
    private String courseID;
    private final List<Attendance> attendancesList = new ArrayList<>();
    private final List<Attendance> alreadyList = new ArrayList<>();
    private final List<Attendance> undoList = new ArrayList<>();
    private DefaultTableModel undoTableModel,alreadyTableModel;
    private static final Logger LOGGER = LogManager.getLogger();

    public Attend() {
        //学生签到信息监听
        if(teacherNet!=null){
            teacherNet.setOnReceiveListener(new ReceiveListener() {
                @Override
                public void onReceive(MessageObject messageObject) {
                    //判断该信息类型是否为签到并且签到状态是否为已签
                    if (messageObject.getMessageType() == MessageType.CheckIn && messageObject.getAttStatus() == AttStatus.signed) {
                        try {
                            //将学生签到状态修改进数据表中
                            DatabaseHelper.updateAttendance(messageObject.getStuNo(), messageObject.getAttStatus(),messageObject.getLocalDateTime());
                        } catch (NoConfigException e) {
                            JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
                            LOGGER.error("No configuration", e);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "数据库出错", "警告", JOptionPane.ERROR_MESSAGE);
                            LOGGER.error("SQLException", e);
                        } catch (DependenciesNotFoundException e) {
                            LOGGER.error("DependenciesNotFoundException", e);
                        }
                    }
                }
            });
        }

        //未签表格的鼠标点击事件
        table_undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获取鼠标所点击的行和列
                int row = table_undo.getSelectedRow();
                int col = table_undo.getSelectedColumn();
                //修改学生签到状态
                if (col == 3) {
                    Object[] options = {AttStatus.absence, AttStatus.signed, AttStatus.leave_early, AttStatus.late, AttStatus.personal_leave, AttStatus.public_holiday, AttStatus.sick_leave, AttStatus.not_signed};
                    AttStatus attStatus = (AttStatus) JOptionPane.showInputDialog(null, "选择您所要修改的签到状态", "提示",
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (attStatus != null) {
                        String modifyStat = String.valueOf(attStatus);
                        //将修改后的状态显示出来
                        table_undo.getModel().setValueAt(modifyStat, row, col);
                        //将修改在数据库中更改
                        try {
                            LocalDateTime currentTime = LocalDateTime.now();
                            DatabaseHelper.updateAttendance(String.valueOf(table_undo.getModel().getValueAt(row, 0)), attStatus, currentTime);
                        } catch (NoConfigException ex) {
                            JOptionPane.showMessageDialog(null, "没有数据库配置文件", "警告", JOptionPane.ERROR_MESSAGE);
                            LOGGER.error("No configuration", e);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "数据库出错", "警告", JOptionPane.ERROR_MESSAGE);
                            LOGGER.error("SQLException", e);
                        } catch (DependenciesNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "未查询到该数据", "错误", JOptionPane.ERROR_MESSAGE);
                            LOGGER.error("DependenciesNotFoundException", e);
                        }
                        bt_refresh.doClick();
                    }
                }
            }
        });
        //刷新按钮的监听
        bt_refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //清空列表
                ((DefaultTableModel)table_already.getModel()).setRowCount(0);
                ((DefaultTableModel)table_undo.getModel()).setRowCount(0);
                attendancesList.clear();
                alreadyList.clear();
                undoList.clear();
                //重新加载
                try {
                    attendancesList.addAll(DatabaseHelper.selectAttendanceByCourse(Long.parseLong(courseID)));  //导入该课程考勤名单
                    for (Attendance attendance : attendancesList) {
                        if (attendance.getAttStatus() == AttStatus.signed) {        //筛选出已签到的考勤列表
                            alreadyList.add(attendance);
                        } else {
                            undoList.add(attendance);
                        }
                    }
                } catch (DependenciesNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (NoConfigException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //已签考勤表格
                alreadyTableModel = new DefaultTableModel(t1_columnTitle, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                if (alreadyList != null) {
                    for (Attendance attendance : alreadyList) {
                        String stuID = String.valueOf(attendance.getStudent().getStuID());
                        String stuName = String.valueOf(attendance.getStudent().getStuName());
                        LocalDateTime attTime = attendance.getAttTime();
                        String attTime1 = attTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        Object row[] = {stuID, stuName, attTime1};
                        alreadyTableModel.addRow(row);
                    }
                }
                attend.table_already.setModel(alreadyTableModel);

                //未签考勤表格
                undoTableModel = new DefaultTableModel(t2_columnTitle, 0);
                for (Attendance attendance : undoList) {
                    String attID = attendance.getAttID();
                    String stuID = String.valueOf(attendance.getStudent().getStuID());
                    String stuName = String.valueOf(attendance.getStudent().getStuName());
                    AttStatus attStatus = attendance.getAttStatus();
                    Object row[] = {attID, stuID, stuName, String.valueOf(attStatus)};
                    undoTableModel.addRow(row);
                }
                attend.table_undo.setModel(undoTableModel);
                //设置第一列隐藏
                TableColumn tableColumn = attend.table_undo.getColumnModel().getColumn(0);
                tableColumn.setWidth(0);
                tableColumn.setMinWidth(0);
                tableColumn.setPreferredWidth(0);
                tableColumn.setMaxWidth(0);
                attend.table_undo.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
                attend.table_undo.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
                attend.table_undo.setCellSelectionEnabled(true);

            }
        });
    }


    public static void main(String[] args) {
        frame.setContentPane(attend.rootPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

    }

    public void start(TeacherNet teacherNet1, String courseID) {
        frame.setContentPane(attend.rootPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        attend.teacherNet = teacherNet1;
        attend.courseID = courseID;
        try {
            attendancesList.addAll(DatabaseHelper.selectAttendanceByCourse(Long.parseLong(courseID)));  //导入该课程考勤名单
            LOGGER.debug("attendanceList:" + attendancesList);
            for (Attendance attendance : attendancesList) {
                if (attendance.getAttStatus() == AttStatus.signed) {        //筛选出已签到的考勤列表
                    alreadyList.add(attendance);
                } else {
                    undoList.add(attendance);
                }
            }
            LOGGER.debug("alreadyList:" + alreadyList);
            LOGGER.debug("undoList:" + undoList);
        } catch (DependenciesNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoConfigException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //已签考勤表格
        alreadyTableModel = new DefaultTableModel(t1_columnTitle, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (alreadyList != null) {
            for (Attendance attendance : alreadyList) {
                String stuID = String.valueOf(attendance.getStudent().getStuID());
                String stuName = String.valueOf(attendance.getStudent().getStuName());
                LocalDateTime attTime = attendance.getAttTime();
                String attTime1 = attTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Object row[] = {stuID, stuName, attTime1};
                alreadyTableModel.addRow(row);
            }
        }
        attend.table_already.setModel(alreadyTableModel);

        //未签考勤表格
        undoTableModel = new DefaultTableModel(t2_columnTitle, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Attendance attendance : undoList) {
            String attID = attendance.getAttID();
            String stuID = String.valueOf(attendance.getStudent().getStuID());
            String stuName = String.valueOf(attendance.getStudent().getStuName());
            AttStatus attStatus = attendance.getAttStatus();
            Object row[] = {attID, stuID, stuName, String.valueOf(attStatus)};
            undoTableModel.addRow(row);
        }
        attend.table_undo.setModel(undoTableModel);
        //设置第一列隐藏
        TableColumn tableColumn = attend.table_undo.getColumnModel().getColumn(0);
        tableColumn.setWidth(0);
        tableColumn.setMinWidth(0);
        tableColumn.setPreferredWidth(0);
        tableColumn.setMaxWidth(0);
        attend.table_undo.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        attend.table_undo.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        attend.table_undo.setCellSelectionEnabled(true);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }

}
