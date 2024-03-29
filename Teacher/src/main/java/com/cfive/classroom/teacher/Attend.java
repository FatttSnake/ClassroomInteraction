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
    private static final JFrame frame = new JFrame("考勤情况");
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private final Object[] t1_columnTitle = {"考勤号", "学号", "姓名", "签到时间", "签到状态"};
    private final Object[] t2_columnTitle = {"考勤号", "学号", "姓名", "签到状态"};
    private JTable table_already;
    private JTable table_undo;
    private JButton bt_refresh;
    private String courseID;
    private final List<Attendance> attendancesList = new ArrayList<>();
    private final List<Attendance> alreadyList = new ArrayList<>();
    private final List<Attendance> undoList = new ArrayList<>();
    private DefaultTableModel undoTableModel, alreadyTableModel;
    private static final Logger LOGGER = LogManager.getLogger();


    public Attend(TeacherNet teacherNet, String courseID) {
        this.courseID = courseID;

        //已签表格的鼠标点击事件
        table_already.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获取鼠标所点击的行和列
                int row = table_already.getSelectedRow();
                int col = table_already.getSelectedColumn();
                //修改学生签到状态
                if (col == 4) {
                    Object[] options = {AttStatus.absence, AttStatus.signed, AttStatus.leave_early, AttStatus.late, AttStatus.personal_leave, AttStatus.public_holiday, AttStatus.sick_leave, AttStatus.not_signed};
                    AttStatus attStatus = (AttStatus) JOptionPane.showInputDialog(null, "选择您所要修改的签到状态", "提示",
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (attStatus != null) {
                        String modifyStat = String.valueOf(attStatus);
                        //将修改后的状态显示出来
                        table_already.getModel().setValueAt(modifyStat, row, col);
                        //将修改在数据库中更改
                        try {
                            LOGGER.info("test");
                            DatabaseHelper.updateAttendance(String.valueOf(table_already.getModel().getValueAt(row, 0)), attStatus, null);
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
                        //刷新表格
                        bt_refresh.doClick();
                    }
                }
            }
        });


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
                        //如果修改为已签则刷新表格
                        if (attStatus == AttStatus.signed) {
                            bt_refresh.doClick();
                        }

                    }
                }
            }
        });
        //刷新按钮的监听
        bt_refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //清空列表
                ((DefaultTableModel) table_already.getModel()).setRowCount(0);
                ((DefaultTableModel) table_undo.getModel()).setRowCount(0);
                attendancesList.clear();
                alreadyList.clear();
                undoList.clear();
                //重新加载
                readData();
            }
        });
    }


    public static void main(String[] args) {
    }

    public void start() {
        frame.setContentPane(rootPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        readData();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }

    private void readData(){
        try {
            attendancesList.addAll(DatabaseHelper.selectAttendanceByCourse(Long.parseLong(courseID)));  //导入该课程考勤名单
            for (Attendance attendance : attendancesList) {
                if (attendance.getAttStatus() == AttStatus.signed) {        //筛选出已签到的考勤列表
                    alreadyList.add(attendance);
                } else {
                    undoList.add(attendance);                               //筛选出未签到成功的列表
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
        if (!alreadyList.isEmpty()) {
            for (Attendance attendance : alreadyList) {
                String attID = attendance.getAttID();
                String stuID = String.valueOf(attendance.getStudent().getStuID());
                String stuName = String.valueOf(attendance.getStudent().getStuName());
                LocalDateTime attTime = attendance.getAttTime();
                AttStatus attStatus = attendance.getAttStatus();
                String attTime1 = attTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Object row[] = {attID, stuID, stuName, attTime1, String.valueOf(attStatus)};
                alreadyTableModel.addRow(row);
            }
        }
        table_already.setModel(alreadyTableModel);
        //设置未签表格的第一列考勤号隐藏
        TableColumn tableColumn1 = table_already.getColumnModel().getColumn(0);
        tableColumn1.setWidth(0);
        tableColumn1.setMinWidth(0);
        tableColumn1.setPreferredWidth(0);
        tableColumn1.setMaxWidth(0);
        table_already.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        table_already.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        table_already.setCellSelectionEnabled(true);

        //未签考勤表格
        if (!undoList.isEmpty()) {         //判断该课程是否有学生名单
            undoTableModel = new DefaultTableModel(t2_columnTitle, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (Attendance attendance : undoList) {                //遍历插入数据
                String attID = attendance.getAttID();
                String stuID = String.valueOf(attendance.getStudent().getStuID());
                String stuName = String.valueOf(attendance.getStudent().getStuName());
                AttStatus attStatus = attendance.getAttStatus();
                Object row[] = {attID, stuID, stuName, String.valueOf(attStatus)};
                undoTableModel.addRow(row);
            }
            table_undo.setModel(undoTableModel);
            //设置未签表格的第一列考勤号隐藏
            TableColumn tableColumn2 = table_undo.getColumnModel().getColumn(0);
            tableColumn2.setWidth(0);
            tableColumn2.setMinWidth(0);
            tableColumn2.setPreferredWidth(0);
            tableColumn2.setMaxWidth(0);
            table_undo.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            table_undo.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            table_undo.setCellSelectionEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "该课程学生名单未导入", "提醒！", JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
