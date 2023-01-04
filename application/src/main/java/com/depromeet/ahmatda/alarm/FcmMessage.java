package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmMessage {

    private Message message;

    @Builder
    @Getter
    public static class Message {
        private String token;
        private Notification notification;
    }

    @Builder
    @Getter
    public static class Notification {
        private String title;
        private String body;

        public static Notification createNotificationByAlarmEntity(Alarm alarm) {
            return Notification.builder()
                    .title(alarm.getTemplate().getTemplateName())
                    .body(alarm.getTemplate().getTemplateName())
                    .build();
        }
    }
}
