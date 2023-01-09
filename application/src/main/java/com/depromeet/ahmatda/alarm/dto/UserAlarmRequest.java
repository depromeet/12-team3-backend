package com.depromeet.ahmatda.alarm.dto;

import com.depromeet.ahmatda.domain.alarm.TimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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
    private TimeOption timeOption;
}
