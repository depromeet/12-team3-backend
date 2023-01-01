package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.AlarmTimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserAlarmRequest {
    @NotNull
    private AlarmType alarmType;
    @NotNull
    private Long templateId;
    @NotNull
    private Boolean isActivated;
    @NotNull
    private LocalDateTime alarmDateTime;
    @NotNull
    private AlarmTimeOption alarmTimeOption;
}
