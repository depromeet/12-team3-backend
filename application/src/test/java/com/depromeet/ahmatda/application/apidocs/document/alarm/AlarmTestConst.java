package com.depromeet.ahmatda.application.apidocs.document.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmTimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;

import java.time.LocalDateTime;

public class AlarmTestConst {

    final static String USER_TOKEN = "abcde";
    final static long USER_ID = 13L;
    final static long TEMPLATE_ID = 100L;

    final static Alarm dailyAlarm = Alarm.builder()
            .id(1L)
            .pushToken("token")
            .isActivated(true)
            .isSend(false)
            .templateId(1L)
            .alarmType(AlarmType.DAILY)
            .alarmDateTime(LocalDateTime.of(2023, 1, 1, 13, 55, 1))
            .timeOption(AlarmTimeOption.ONE_DAY)
            .build();
}





