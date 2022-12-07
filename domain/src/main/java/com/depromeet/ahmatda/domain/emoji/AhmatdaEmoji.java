package com.depromeet.ahmatda.domain.emoji;

import lombok.Getter;

@Getter
public enum AhmatdaEmoji {

    BOWLING("GraphicBowling"),
    BUS("GraphicBus"),
    CAMERA("GraphicCamera"),
    EMPTY_CARD("GraphicEmptyCard"),
    ETC("GraphicEtc"),
    FRIENDS("GraphicFriends"),
    GYM("GraphicGym"),
    PLANE("GraphicPlane"),
    RUN("GraphicRun"),
    SCHOOL("GraphicSchool"),
    SWIM("GraphicSwim"),
    TUBE("GraphicTube"),
    WORK("GraphicWork");

    private final String frontValue;

    AhmatdaEmoji(String frontValue) {
        this.frontValue = frontValue;
    }
}
