package com.depromeet.ahmatda.alarm.history.dto;

import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlarmMessageHistoryResponse {

    private AhmatdaEmoji ahmatdaEmoji;
    private String message;
    private String sentTime;

}
