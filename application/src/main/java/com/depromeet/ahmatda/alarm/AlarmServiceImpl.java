package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmAdaptor;
import com.depromeet.ahmatda.domain.alarm.AlarmInfo;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

    private final AlarmAdaptor alarmAdaptor;
    private final TemplateAdaptor templateAdaptor;

    @Override
    public String getAlarmInfo(User user, Long templateId) {
        Alarm alarm = getAlarm(user, templateId);
        return AlarmInfo.getDailyAlarmInfo(alarm);
    }

    @Override
    public Alarm getAlarm(User user, Long templateId) {
        validateAndGetTemplate(user, templateId);
        return alarmAdaptor.findAlarmByTemplateId(templateId).orElse(null);
    }

    @Override
    public void setTemplateAlarm(final User user, final UserAlarmRequest userAlarmRequest) {

        final Template template = validateAndGetTemplate(user, userAlarmRequest.getTemplateId());

        alarmAdaptor.findAlarmByTemplateId(template.getId())
            .ifPresent(t -> {
                throw new AlarmExistException(ErrorCode.ALARM_EXIST);
            });

        final Alarm alarm = Alarm.createDaily(
            user.getFcmToken(),
            userAlarmRequest.getTemplateId(),
            userAlarmRequest.getIsActivated(),
            userAlarmRequest.getAlarmTime(),
            userAlarmRequest.getDailyAlarmOption()
        );

        alarmAdaptor.save(alarm);
    }

    private Template validateAndGetTemplate(final User user, final Long templateId) {
        final Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        if (!Objects.equals(template.getUser().getId(), user.getId())) {
            log.error("userId 와 template 의 userId 가 다릅니다.");
            throw new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND);
        }

        return template;
    }
}
