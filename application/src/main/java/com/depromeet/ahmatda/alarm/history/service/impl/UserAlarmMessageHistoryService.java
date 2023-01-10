package com.depromeet.ahmatda.alarm.history.service.impl;

import com.depromeet.ahmatda.alarm.history.dto.AlarmMessageHistoryResponse;
import com.depromeet.ahmatda.alarm.history.service.AlarmMessageHistoryService;
import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import com.depromeet.ahmatda.domain.alarm.adaptor.AlarmMessageHistoryAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAlarmMessageHistoryService implements AlarmMessageHistoryService {

    private final AlarmMessageHistoryAdaptor alarmMessageHistoryAdaptor;

    @Override
    public List<AlarmMessageHistoryResponse> getUserAlarmHistories(String userToken) {
        List<AlarmMessageHistory> alarmMessageHistories = alarmMessageHistoryAdaptor.getUserAlarmHistories(userToken);
        return null;
    }
}
