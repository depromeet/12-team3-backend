package com.depromeet.ahmatda.alarm.fcm.message.concept;

import java.util.Random;

public class MessageConceptFactory {

    public static final String[] concepts = {
            "BASIC",
            "CHEERFUL",
            "SERIOUS"
    };


    public static MessageConcept createMessageConcept() {
        int random = new Random().nextInt(3);
        String randomConcept = concepts[random];
        if (randomConcept == "BASIC") {
            return new BasicMessage();
        } else if (randomConcept == "CHEERFUL") {
            return new CheerfulMessage();
        } else if (randomConcept == "SERIOUS") {
            return new SeriousMessage();
        }

        return new BasicMessage();
    }
}
