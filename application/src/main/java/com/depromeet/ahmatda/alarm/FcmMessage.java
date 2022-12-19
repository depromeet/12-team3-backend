package com.depromeet.ahmatda.alarm;

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
    }
}
