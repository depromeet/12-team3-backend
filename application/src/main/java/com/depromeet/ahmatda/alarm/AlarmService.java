package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.user.User;

public interface AlarmService {
    void setTemplateAlarm(final User user, final UserAlarmRequest userAlarmRequest);
}
