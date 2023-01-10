package com.depromeet.ahmatda.alarm.history;


import java.time.Duration;
import java.time.LocalDateTime;

public class HistoryTimeCalculator {

    public static final long ZERO_MINUTES = 0;
    public static final long ONE_HOUR_TO_MINUTE = 60;
    public static final long ONE_DAY_TO_HOURS = 24;
    public static final String NOW = "방금 전";
    public static final String MINUTES_AGO = "분 전";
    public static final String HOURS_AGO = "시간 전";
    public static final String DAYS_AGO = "일 전";

    private HistoryTimeCalculator() {
    }

    public static String calculateElapsedTime(LocalDateTime sentAt, LocalDateTime nowTime) {
        Duration between = Duration.between(sentAt, nowTime);
        long passingMinutes = between.toMinutes();
        long passingHours = between.toHours();
        long passingDays = between.toDays();

        if (passingMinutes == ZERO_MINUTES) {
            return NOW;
        } else if (passingMinutes < ONE_HOUR_TO_MINUTE) {
            return passingMinutes + MINUTES_AGO;
        } else if (passingHours < ONE_DAY_TO_HOURS) {
            return passingHours + HOURS_AGO;
        }
        return passingDays + DAYS_AGO;
    }
}
