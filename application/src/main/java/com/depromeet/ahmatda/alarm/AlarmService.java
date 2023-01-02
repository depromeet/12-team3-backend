package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.user.User;

import java.util.Optional;

public interface AlarmService {
    String getAlarmInfo(final Long userId, final Long templateId);
    Optional<Alarm> getAlarm(final Long userId, final Long templateId);
    Alarm setTemplateDailyAlarm(final User user, final UserAlarmRequest userAlarmRequest);
}
