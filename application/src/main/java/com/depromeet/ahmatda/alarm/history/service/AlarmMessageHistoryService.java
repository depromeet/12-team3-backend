package com.depromeet.ahmatda.alarm.history.service;

import com.depromeet.ahmatda.alarm.history.dto.AlarmMessageHistoryResponse;

import java.util.List;

public interface AlarmMessageHistoryService {

    List<AlarmMessageHistoryResponse> getUserAlarmHistories(String userToken);
}
