package com.depromeet.ahmatda.apidocs.util;

import org.springframework.restdocs.snippet.Attributes;


public interface DocumentFormatGenerator {

    static Attributes.Attribute getDateTimeFormat() {
        return Attributes.key("format").value("yyyy-mm-dd HH:mm:ss");
    }

    static Attributes.Attribute getDateFormat() {
        return Attributes.key("format").value("yyyy-mm-dd");
    }

    static Attributes.Attribute getTimeFormat() {
        return Attributes.key("format").value("HH:mm:ss");
    }
}
