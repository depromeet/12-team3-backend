package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.AlarmTimeOption;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAlarmRequest {
    private Long templateId;
    private Boolean isActivated;
    private LocalDateTime alarmTime;
    private AlarmTimeOption dailyAlarmOption;
}
