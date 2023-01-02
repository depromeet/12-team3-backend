package com.depromeet.ahmatda.application.apidocs.document.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmTimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import com.depromeet.ahmatda.domain.template.Template;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

public class AlarmTestConst {

    final static String USER_TOKEN = "abcde";
    final static long USER_ID = 13L;
    final static long TEMPLATE_ID = 100L;

    final static Alarm dailyAlarm = Alarm.builder()
            .id(1L)
            .isActivated(true)
            .isSend(false)
            .template(mock(Template.class))
            .alarmType(AlarmType.DAILY)
            .alarmDateTime(LocalDateTime.of(2023, 1, 1, 13, 55, 1))
            .timeOption(AlarmTimeOption.ONE_DAY)
            .build();
}





