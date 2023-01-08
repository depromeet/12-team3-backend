package com.depromeet.ahmatda.domain.alarm;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AlarmMessageHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    private String message;

    private LocalDateTime sentAt;
}
