package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.user.User;

public interface AlarmService {
    Alarm getAlarm(final User user, final Long templateId);
    void setTemplateAlarm(final User user, final UserAlarmRequest userAlarmRequest);
}
