package com.depromeet.ahmatda.domain.alarm;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@DynamicUpdate
@Table(name = "alarm")
public class Alarm extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_activated")
    private boolean isActivated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

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
    private TimeOption timeOption;

    public static Alarm createDaily(Template template, boolean isActivated, LocalDateTime alarmDateTime, TimeOption timeOption) {
        return Alarm.builder()
            .isActivated(isActivated)
            .template(template)
            .isSend(false)
            .alarmType(AlarmType.DAILY)
            .alarmDateTime(alarmDateTime)
            .timeOption(timeOption)
            .build();
    }

    public void updateDailyAlarm(boolean isActivated, LocalDateTime alarmDateTime, TimeOption timeOption) {
        this.isActivated = isActivated;
        this.alarmDateTime = alarmDateTime;
        this.timeOption = timeOption;
    }
}
