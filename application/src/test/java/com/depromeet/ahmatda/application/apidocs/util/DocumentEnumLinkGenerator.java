package com.depromeet.ahmatda.application.apidocs.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DocumentEnumLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @RequiredArgsConstructor
    enum DocUrl {
        ONBOARDING_CATEGORY("onboardingCategory", "온보딩_카테고리"),
        USER_REGISTER_CODE("userRegisterCode", "유저_등록"),
        TIME_OPTION("timeOption", "알람_시간_조절_옵션"),
        ALARM_TYPE("alarmType", "알람_타입");

        private final String pageId;
        @Getter
        private final String text;
    }
}
