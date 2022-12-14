package com.depromeet.ahmatda.domain.alarm.adaptor;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.repository.AlarmRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmAdaptor {

    private final AlarmRepository alarmRepository;

    public Alarm save(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public Optional<Alarm> findAlarmByTemplateId(Long templateId) {
        return alarmRepository.findByTemplateId(templateId);
    }

    public List<Alarm> findUnsentAlarm() {
        return alarmRepository.findAllUnsentAlarm();
    }
}
