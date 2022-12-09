package com.depromeet.ahmatda.domain.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    BICEPS("\\xF0\\x9F\\x92\\xAA", "U+1F4AA"),
    BRIEFCASE("\\xF0\\x9F\\x92\\xBC", "U+1F4BC"),
    AIRPLANE("\\xE2\\x9C\\x88", "U+2708"),

    EXCEPTION("", "");

    private final String utf8;
    private final String unicode;

    @JsonCreator
    public static Emoji emojiValidator(String requestEmoji) {
        Emoji[] emojis = Emoji.values();
        for (Emoji emoji : emojis) {
            if (requestEmoji.equals(emoji.toString())) {
                return emoji;
            }
        }
        return Emoji.EXCEPTION;
    }
}
