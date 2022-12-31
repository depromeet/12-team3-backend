package com.depromeet.ahmatda.common.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {

    public static String getCronFromLocalDateTime(LocalDateTime dateTime) {
        return String.format("%d %d %d %d %d %d",
            dateTime.getSecond(),
            dateTime.getMinute(),
            dateTime.getHour(),
            dateTime.getDayOfMonth(),
            dateTime.getMonthValue(),
            dateTime.getDayOfWeek().getValue()
        );
    }

    public static String getCronFromLocalDateTimeWithYear(LocalDateTime dateTime) {
        return getCronFromLocalDateTime(dateTime) + " " + dateTime.getYear();
    }
}
