package com.depromeet.ahmatda.fcm.message.concept;

import com.depromeet.ahmatda.fcm.message.MessageConst;
import com.depromeet.ahmatda.domain.alarm.Alarm;

public class CheerfulMessage extends MessageConcept {

    public CheerfulMessage() {
        this.titleMessage = MessageConst.CHEERFUL_ALARM_TITLE_POSTFIX;
        this.bodyMessage = MessageConst.CHEERFUL_ALARM_BODY_POSTFIX;
    }

    @Override
    public String makeTitle(Alarm alarm) {
        return "[" + alarm.getTemplateName() + "]" + titleMessage;
    }

    @Override
    public String makeBody(String itemNames) {
        return itemNames + bodyMessage;
    }
}
