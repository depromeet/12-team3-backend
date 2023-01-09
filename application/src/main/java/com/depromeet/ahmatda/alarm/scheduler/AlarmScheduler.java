package com.depromeet.ahmatda.alarm.scheduler;

import com.depromeet.ahmatda.fcm.service.impl.FcmPushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("production")
public class AlarmScheduler {

    public static final long SCHEDULER_REPEAT_CYCLE = 10000L;

    private final FcmPushService fcmPushService;

    @Scheduled(fixedDelay = SCHEDULER_REPEAT_CYCLE)
    @Transactional
    public void sendAlarmSchedule() throws IOException {
        fcmPushService.sendAlarms();
    }
}
