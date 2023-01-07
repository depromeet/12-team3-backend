package com.depromeet.ahmatda.domain.alarm.dto;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmDayOfWeekType;
import com.depromeet.ahmatda.domain.alarm.AlarmReplayType;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import com.depromeet.ahmatda.domain.alarm.TimeOption;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class AlarmResponse {

    private long templateId;
    private AlarmType alarmType;
    private AlarmDayOfWeekType dayOfWeek;
    private LocalTime alarmTime;
    private AlarmReplayType replayType;
    private LocalDateTime alarmDateTime;
    private TimeOption timeOption;
    private boolean activated;

    public static AlarmResponse of(Alarm alarm) {
        if (alarm == null) {
            return null;
        }

        return AlarmResponse.builder()
                .templateId(alarm.getTemplate().getId())
                .alarmType(alarm.getAlarmType())
                .dayOfWeek(alarm.getDayOfWeek())
                .alarmTime(alarm.getAlarmTime())
                .replayType(alarm.getReplayType())
                .alarmDateTime(alarm.getAlarmDateTime())
                .timeOption(alarm.getTimeOption())
                .activated(alarm.isActivated())
                .build();
    }
}
