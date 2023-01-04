package com.depromeet.ahmatda.alarm.fcm.message.concept;

import com.depromeet.ahmatda.alarm.fcm.message.MessageConst;
import com.depromeet.ahmatda.domain.alarm.Alarm;

public class SeriousMessage extends MessageConcept {

    public SeriousMessage() {
        this.titleMessage = MessageConst.SERIOUS_ALARM_TITLE_POSTFIX;
        this.bodyMessage = MessageConst.SERIOUS_ALARM_BODY_POSTFIX;
    }

    @Override
    public String makeTitle(final Alarm alarm) {
        return "[" + alarm.getTemplateName() + "]" + titleMessage;
    }

    @Override
    public String makeBody(String itemNames) {
        return itemNames + bodyMessage;
    }
}
