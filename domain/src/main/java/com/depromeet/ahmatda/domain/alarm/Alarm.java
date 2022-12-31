package com.depromeet.ahmatda.domain.alarm;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "template_id")
    private long templateId;

    @Column(name = "reserve_time")
    private String reserveTime;

    @Column(name = "is_send")
    private boolean isSend;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "alarm_form_id")
    private long alarmFormId;

    public static Alarm createAlarm(String token, long templateId, String reservationCron, boolean isActivated) {
        return Alarm.builder()
            .pushToken(token)
            .templateId(templateId)
            .reserveTime(reservationCron)
            .isSend(false)
            .isActivated(isActivated)
            .build();
    }
}
