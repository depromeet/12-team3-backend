package com.depromeet.ahmatda.alarm.scheduler;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.alarm.AlarmAdaptor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmScheduler {

    public static final long SCHEDULER_REPEAT_CYCLE = 10000L;
    private final AlarmAdaptor alarmAdaptor;

    @Scheduled(fixedDelay = SCHEDULER_REPEAT_CYCLE)
    @Async(value = "alarmExecutor")
    public void findUnsentAlarm() {
        LocalDateTime nowTime = LocalDateTime.now().withSecond(0).withNano(0);
        List<Alarm> unsentAlarms = alarmAdaptor.findUnsentAlarm();
        List<Alarm> targetAlarms = unsentAlarms.stream()
            .filter(alarm -> alarm.checkMaximumAlarmOption(alarm, nowTime))
            .filter(alarm -> alarm.isTargetAlarm(alarm, nowTime))
            .collect(Collectors.toList());
    }
}
