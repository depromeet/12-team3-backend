package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmAdaptor;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmAdaptor alarmAdaptor;
    private final TemplateAdaptor templateAdaptor;

    @Override
    public void setTemplateAlarm(final User user, final UserAlarmRequest userAlarmRequest) {

        final Template template = templateAdaptor.getTemplateById(userAlarmRequest.getTemplateId())
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        if (!Objects.equals(template.getUser().getId(), user.getId())) {
            throw new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND);
        }

        alarmAdaptor.findAlarmByTemplateId(template.getId())
            .ifPresent(t -> {
                throw new AlarmExistException(ErrorCode.ALARM_EXIST);
            });

        String alarmTime = userAlarmRequest.getReservationCron();

        final Alarm alarm = Alarm.createAlarm(
            user.getFcmToken(), userAlarmRequest.getTemplateId(), alarmTime, userAlarmRequest.getIsActivated()
        );

        alarmAdaptor.save(alarm);
    }
}
