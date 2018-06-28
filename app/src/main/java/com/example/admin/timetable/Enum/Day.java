package com.example.admin.timetable.Enum;

public enum Day {
    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), ETC(7), NEW(8);

    private final int val;
    private Day(int val) {
        this.val = val;
    }
    public int getValue() {
        return val;
    }
}
