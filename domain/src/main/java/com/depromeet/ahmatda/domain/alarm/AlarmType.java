package com.depromeet.ahmatda.domain.alarm;

public enum AlarmType {
    WEEKLY, DAILY;

    public static boolean isDaily(AlarmType alarmType) {
        return DAILY.equals(alarmType);
    }

    public static boolean isWeekly(AlarmType alarmType) {
        return WEEKLY.equals(alarmType);
    }
}
