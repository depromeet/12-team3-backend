package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.AlarmTimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class UserAlarmRequest {
    @NotNull
    private AlarmType alarmType;
    @NotNull
    private Long templateId;
    @NotNull
    private Boolean isActivated;
    @NotNull
    private LocalDateTime alarmTime;
    @NotNull
    private AlarmTimeOption dailyAlarmOption;
}
