package com.depromeet.ahmatda.alarm.fcm.message.concept;

import com.depromeet.ahmatda.alarm.fcm.message.MessageConst;
import com.depromeet.ahmatda.domain.alarm.Alarm;

public class BasicMessage extends MessageConcept {

    public BasicMessage() {
        this.titleMessage = MessageConst.BASIC_ALARM_TITLE_PREFIX;
        this.bodyMessage = MessageConst.BASIC_ALARM_BODY_POSTFIX;
    }

    @Override
    public String makeTitle(Alarm alarm) {
        return titleMessage + "[" + alarm.getTemplateName() + "]";
    }

    @Override
    public String makeBody(String itemNames) {
        return itemNames + bodyMessage;
    }
}
