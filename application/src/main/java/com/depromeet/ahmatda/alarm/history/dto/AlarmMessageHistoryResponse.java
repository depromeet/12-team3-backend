package com.depromeet.ahmatda.alarm.history.dto;

import com.depromeet.ahmatda.alarm.history.HistoryTimeCalculator;
import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AlarmMessageHistoryResponse {

    private AhmatdaEmoji ahmatdaEmoji;
    private String message;
    private String elapsedSentTime;

    public static AlarmMessageHistoryResponse createByAlarmMessageEntity(AlarmMessageHistory alarmMessageHistory) {
        String elapsedSentTime = calculateElapsedTime(alarmMessageHistory.getSentAt());

        return AlarmMessageHistoryResponse.builder()
                .message(alarmMessageHistory.getMessage())
                .elapsedSentTime(elapsedSentTime)
                .build();
    }

    private static String calculateElapsedTime(LocalDateTime sentAt) {
        LocalDateTime nowTime = LocalDateTime.now().withSecond(0).withNano(0);
        return HistoryTimeCalculator.calculateElapsedTime(sentAt, nowTime);
    }
}
