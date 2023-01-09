package com.depromeet.ahmatda.domain.alarm.adaptor;

import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import com.depromeet.ahmatda.domain.alarm.repository.AlarmMessageHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmMessageHistoryAdaptor {

    private final AlarmMessageHistoryRepository alarmHistoryMessageRepository;

    public void save(AlarmMessageHistory alarmMessageHistory) {
        alarmHistoryMessageRepository.save(alarmMessageHistory);
    }
}
