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

    public static AttStatus fromString(String s) {
        if (s.equals("signed")) {
            return signed;
        }
        if (s.equals("absence")) {
            return absence;
        }
        if (s.equals("personal_leave")) {
            return personal_leave;
        }
        if (s.equals("sick_leave")) {
            return sick_leave;
        }
        if (s.equals("public_holiday")) {
            return public_holiday;
        }
        if (s.equals("late")) {
            return late;
        }
        if (s.equals("leave_early")) {
            return leave_early;
        }
        return not_signed;
    }
}
