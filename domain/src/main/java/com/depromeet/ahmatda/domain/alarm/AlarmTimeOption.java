package com.depromeet.ahmatda.domain.alarm;

import java.time.LocalDateTime;
import java.util.function.Function;

public enum AlarmTimeOption {
    ONTIME(t -> t),
    TEM_MINUTES(t -> t.minusMinutes(10)),
    THIRTY_MINUTES(t -> t.minusMinutes(30)),
    ONE_HOUR(t -> t.minusHours(1)),
    TWO_HOURS(t -> t.minusHours(2)),
    ONE_DAY(t -> t.minusDays(1)),
    TWO_DAYS(t -> t.minusDays(2)),
    ONE_WEEK(t -> t.minusWeeks(1));

    private final Function<LocalDateTime, LocalDateTime> function;

    AlarmTimeOption(Function<LocalDateTime, LocalDateTime> function) {
        this.function = function;
    }

    public LocalDateTime applyAlarmOption(LocalDateTime time) {
        return function.apply(time);
    }
}
