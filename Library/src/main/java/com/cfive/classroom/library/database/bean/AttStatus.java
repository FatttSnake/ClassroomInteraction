package com.cfive.classroom.library.database.bean;

/**
 * 考勤状态枚举类
 *
 * @author FatttSnake
 * @version 1.0
 */
public enum AttStatus {
    not_signed("未签"), signed("已签"), absence("缺勤"), personal_leave("事假"), sick_leave("病假"), public_holiday("公假"), late("迟到"), leave_early("早退");

    private final String string;

    AttStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
