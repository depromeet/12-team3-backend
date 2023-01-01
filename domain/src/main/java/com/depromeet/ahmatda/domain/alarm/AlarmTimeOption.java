package com.depromeet.ahmatda.domain.alarm;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
public enum AlarmTimeOption {
    ONTIME("정시", t -> t),
    TEM_MINUTES("10분 전", t -> t.minusMinutes(10)),
    THIRTY_MINUTES("30분 전", t -> t.minusMinutes(30)),
    ONE_HOUR("1시간 전", t -> t.minusHours(1)),
    TWO_HOURS("2시간 전", t -> t.minusHours(2)),
    ONE_DAY("하루 전", t -> t.minusDays(1)),
    TWO_DAYS("2일 전", t -> t.minusDays(2)),
    ONE_WEEK("일주일 전", t -> t.minusWeeks(1));

    private final String desc;
    private final Function<LocalDateTime, LocalDateTime> function;

    AlarmTimeOption(String desc, Function<LocalDateTime, LocalDateTime> function) {
        this.desc = desc;
        this.function = function;
    }

    public LocalDateTime applyAlarmOption(LocalDateTime time) {
        return function.apply(time);
    }
}
