package com.depromeet.ahmatda.alarm.history;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class HistoryTimeCalculatorTest {

    @Test
    @DisplayName("한 시간 경과")
    void calculateElapsedTime_one_hour() {
        LocalDateTime sentTime = LocalDateTime.of(2023, 11, 05, 22, 05);
        LocalDateTime nowTIme = LocalDateTime.of(2023, 11, 05, 23, 05);
        String elapsedTime = HistoryTimeCalculator.calculateElapsedTime(sentTime, nowTIme);
        Assertions.assertThat(elapsedTime).isEqualTo("1시간 전");
    }

    @Test
    @DisplayName("하루 경과")
    void calculateElapsedTime_one_day() {
        LocalDateTime sentTime = LocalDateTime.of(2023, 11, 05, 22, 05);
        LocalDateTime nowTIme = LocalDateTime.of(2023, 11, 06, 22, 05);
        String elapsedTime = HistoryTimeCalculator.calculateElapsedTime(sentTime, nowTIme);
        Assertions.assertThat(elapsedTime).isEqualTo("1일 전");
    }

    @Test
    @DisplayName("0분 전 테스트")
    void calculateElapsedTime_zero_minute() {
        LocalDateTime sentTime = LocalDateTime.of(2023, 11, 06, 22, 05);
        LocalDateTime nowTIme = LocalDateTime.of(2023, 11, 06, 22, 05);
        String elapsedTime = HistoryTimeCalculator.calculateElapsedTime(sentTime, nowTIme);
        Assertions.assertThat(elapsedTime).isEqualTo("방금 전");
    }
}
