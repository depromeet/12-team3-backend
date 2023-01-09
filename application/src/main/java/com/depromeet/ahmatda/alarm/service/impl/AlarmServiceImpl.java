package com.depromeet.ahmatda.alarm.service.impl;

import com.depromeet.ahmatda.alarm.dto.UserAlarmRequest;
import com.depromeet.ahmatda.alarm.service.AlarmService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.adaptor.AlarmAdaptor;
import com.depromeet.ahmatda.domain.alarm.AlarmInfo;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

    private final AlarmAdaptor alarmAdaptor;
    private final TemplateAdaptor templateAdaptor;

    @Override
    public String getAlarmInfo(Long userId, Long templateId) {
        return getAlarm(userId, templateId).map(AlarmInfo::getDailyAlarmInfo).orElse(null);
    }

    @Override
    public Optional<Alarm> getAlarm(Long userId, Long templateId) {
        validateAndGetTemplate(userId, templateId);
        return alarmAdaptor.findAlarmByTemplateId(templateId);
    }

    @Override
    @Transactional
    public Alarm setTemplateDailyAlarm(final User user, final UserAlarmRequest userAlarmRequest) {

        final Template template = validateAndGetTemplate(user.getId(), userAlarmRequest.getTemplateId());
        Alarm alarm = createOrUpdateAlarm(userAlarmRequest, template);

        return alarmAdaptor.save(alarm);
    }

    private Alarm createOrUpdateAlarm(UserAlarmRequest userAlarmRequest, Template template) {
        Optional<Alarm> alarm = alarmAdaptor.findAlarmByTemplateId(template.getId());

        return alarm.map(value -> setAlarmIfExist(value, userAlarmRequest))
                .orElseGet(() -> createDailyAlarm(userAlarmRequest, template));
    }

    private Alarm setAlarmIfExist(Alarm alarm, UserAlarmRequest userAlarmRequest) {

        if (AlarmType.isWeekly(userAlarmRequest.getAlarmType())) {
            // TODO : Weekly 알림 설정이 구현되면 여기서 기존 알람을 Weekly 알람으로 바꿔서 반환
            throw new UnsupportedOperationException();
        } else if (AlarmType.isDaily(userAlarmRequest.getAlarmType())) {
            alarm.updateDailyAlarm(
                userAlarmRequest.getIsActivated(), userAlarmRequest.getAlarmDateTime(), userAlarmRequest.getTimeOption()
            );
        }

        return alarm;
    }

    private Alarm createDailyAlarm(UserAlarmRequest userAlarmRequest, Template template) {
        return Alarm.createDaily(
                template,
                userAlarmRequest.getIsActivated(),
                userAlarmRequest.getAlarmDateTime(),
                userAlarmRequest.getTimeOption()
        );
    }

    private Template validateAndGetTemplate(final Long userId, final Long templateId) {
        final Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        if (!Objects.equals(template.getUser().getId(), userId)) {
            log.error("userId 와 template 의 userId 가 다릅니다.");
            throw new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND);
        }

        return template;
    }
}
