package com.depromeet.ahmatda.fcm.message;

import com.depromeet.ahmatda.fcm.message.concept.MessageConcept;
import com.depromeet.ahmatda.fcm.message.concept.MessageConceptFactory;
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

        public static Notification createNotificationByAlarmEntity(final Alarm alarm) {
            final String itemNames = alarm.getTemplate().getItems().stream()
                    .filter(item -> !item.isTake())
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            final MessageConcept messageConcept = MessageConceptFactory.createMessageConcept();
            final String titleMessage = messageConcept.makeTitle(alarm);
            final String bodyMessage = messageConcept.makeBody(itemNames);

            return Notification.builder()
                    .title(titleMessage)
                    .body(bodyMessage)
                    .build();
        }
    }
}
