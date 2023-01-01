package com.depromeet.ahmatda.domain.alarm;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alarm")
public class Alarm extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "template_id")
    private long templateId;

    @Column(name = "is_send")
    private boolean isSend;

    @Column(name = "alarm_type")
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private AlarmDayOfWeekType dayOfWeek;

    @Column(name = "alarm_time")
    private LocalTime alarmTime;

    @Column(name = "replay_type")
    @Enumerated(EnumType.STRING)
    private AlarmReplayType replayType;

    @Column(name = "alarm_date_time")
    private LocalDateTime alarmDateTime;

    @Column(name = "time_option")
    @Enumerated(EnumType.STRING)
    private AlarmTimeOption timeOption;

    public static Alarm createDaily(String token, long templateId, boolean isActivated, LocalDateTime alarmDateTime, AlarmTimeOption timeOption) {
        return Alarm.builder()
            .pushToken(token)
            .isActivated(isActivated)
            .templateId(templateId)
            .isSend(false)
            .alarmType(AlarmType.DAILY)
            .alarmDateTime(alarmDateTime)
            .timeOption(timeOption)
            .build();
    }
}
