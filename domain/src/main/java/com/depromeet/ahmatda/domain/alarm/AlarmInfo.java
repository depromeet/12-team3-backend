package com.depromeet.ahmatda.domain.alarm;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlarmInfo {

    private final Alarm alarm;

    public AlarmInfo(Alarm alarm) {
        this.alarm = alarm;
    }

    private static final String DAILY_INFO_FORMAT = "%s %d시 %d분 %s";

    public static String getDailyAlarmInfo(Alarm alarm) {
        LocalDateTime alarmDateTime = alarm.getAlarmDateTime();

        LocalDate localDate = alarmDateTime.toLocalDate();
        int hour = alarmDateTime.getHour();
        int minute = alarmDateTime.getMinute();
        String timeOption = alarm.getTimeOption().getLabel();

        return String.format(DAILY_INFO_FORMAT, localDate, hour, minute, timeOption);
    }
}
