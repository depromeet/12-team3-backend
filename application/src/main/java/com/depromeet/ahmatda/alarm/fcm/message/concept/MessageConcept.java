package com.depromeet.ahmatda.alarm.fcm.message.concept;

import com.depromeet.ahmatda.domain.alarm.Alarm;

public abstract class MessageConcept {
    String titleMessage;
    String bodyMessage;

    public abstract String makeTitle(Alarm alarm);
    public abstract String makeBody(String itemNames);
}
