package com.depromeet.ahmatda.domain.alarm;

import com.depromeet.ahmatda.common.utils.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType implements EnumType {
    WEEKLY("요일별"), DAILY("날짜별");

    private final String label;

    public static boolean isDaily(AlarmType alarmType) {
        return DAILY.equals(alarmType);
    }

    public static boolean isWeekly(AlarmType alarmType) {
        return WEEKLY.equals(alarmType);
    }

    @Override
    public String getName() {
        return this.name();
    }
}
