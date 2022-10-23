package com.depromeet.ahmatda.apidocs.util;

import org.springframework.restdocs.snippet.Attributes;

import java.util.function.Function;

public interface DocumentFormatGenerator {

    String FORMAT_KEY = "format";
    Function<String, Attributes.Attribute> getFormatAttribute = format -> Attributes.key(FORMAT_KEY).value(format);

    static Attributes.Attribute getDateTimeFormat() {
        return getFormatAttribute.apply("yyyy-mm-dd HH:mm:ss");
    }

    static Attributes.Attribute getDateFormat() {
        return getFormatAttribute.apply("yyyy-mm-dd");
    }

    static Attributes.Attribute getTimeFormat() {
        return getFormatAttribute.apply("HH:mm:ss");
    }
}
