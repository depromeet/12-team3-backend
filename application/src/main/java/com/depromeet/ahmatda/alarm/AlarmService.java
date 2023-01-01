package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.user.User;

public interface AlarmService {
    String getAlarmInfo(final Long userId, final Long templateId);
    Alarm getAlarm(final Long userId, final Long templateId);
    void setTemplateAlarm(final User user, final UserAlarmRequest userAlarmRequest);
}
