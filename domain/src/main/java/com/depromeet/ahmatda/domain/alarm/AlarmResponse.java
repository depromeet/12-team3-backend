package com.depromeet.ahmatda.domain.alarm;

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
    private String timeOption;
    private boolean activated;

    public static AlarmResponse of(Alarm alarm) {
        if (alarm == null) {
            return null;
        }

        return AlarmResponse.builder()
                .templateId(alarm.getTemplateId())
                .alarmType(alarm.getAlarmType())
                .dayOfWeek(alarm.getDayOfWeek())
                .alarmTime(alarm.getAlarmTime())
                .replayType(alarm.getReplayType())
                .alarmDateTime(alarm.getAlarmDateTime())
                .timeOption(alarm.getTimeOption().getDesc())
                .activated(alarm.isActivated())
                .build();
    }
}
