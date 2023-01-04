package com.depromeet.ahmatda.alarm.fcm.message;

public abstract class MessageConst {

    private MessageConst() {
    }

    public static final String BASIC_ALARM_TITLE_PREFIX = "아맞다! ";
    public static final String BASIC_ALARM_BODY_POSTFIX = " 모두 챙기셨나요?";
    public static final String CHEERFUL_ALARM_TITLE_POSTFIX = "소지품 챙길 시간이에요!";
    public static final String CHEERFUL_ALARM_BODY_POSTFIX = ". 지금 바로 가방에 쏙!";
    public static final String SERIOUS_ALARM_TITLE_POSTFIX = " 잊은 건 없나요?";
    public static final String SERIOUS_ALARM_BODY_POSTFIX = " 잊지 말고 챙겨요.";
}
