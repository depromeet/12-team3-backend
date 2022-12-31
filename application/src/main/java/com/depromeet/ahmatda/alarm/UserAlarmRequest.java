package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.common.utils.DateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAlarmRequest {
    private Long templateId;
    private Boolean isActivated;
    private LocalDateTime alarmTime;
    private DailyAlarmOption dailyAlarmOption;

    public LocalDateTime getOptionAppliedAlarmTime() {
        return dailyAlarmOption.applyAlarmOption(alarmTime);
    }

    public String getReservationCron() {
        return DateTimeUtils.getCronFromLocalDateTimeWithYear(getOptionAppliedAlarmTime());
    }
}
