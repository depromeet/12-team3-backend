package com.depromeet.ahmatda.alarm.fcm;

import com.depromeet.ahmatda.domain.alarm.Alarm;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Collectors;

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
            String titleMessage = MessageConst.BASIC_ALARM_TITLE_PREFIX + alarm.getTemplate().getTemplateName();

            String itemNames = alarm.getTemplate().getItems().stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            String bodyMessage = itemNames + MessageConst.BASIC_ALARM_BODY_POSTFIX;

            return Notification.builder()
                    .title(titleMessage)
                    .body(bodyMessage)
                    .build();
        }
    }
}
