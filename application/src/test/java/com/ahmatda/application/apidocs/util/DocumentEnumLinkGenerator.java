package com.ahmatda.application.apidocs.util;

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
        //TODO : enum 클래스가 만들어지면 src/docs/asciidoc/common 경로에 enum 의 adoc 파일을 만들고, 해당 파일의 이름을 pageId 로 셋팅
//        COLOR("color", "색")
        ;

        private final String pageId;
        @Getter
        private final String text;
    }
}
