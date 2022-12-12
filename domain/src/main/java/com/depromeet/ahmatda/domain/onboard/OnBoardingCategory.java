package com.depromeet.ahmatda.domain.onboard;

import com.depromeet.ahmatda.common.utils.EnumType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnBoardingCategory implements EnumType {

    DAILY(AhmatdaEmoji.WORK, "일상"),
    EXERCISE(AhmatdaEmoji.GYM, "운동"),
    TRAVEL(AhmatdaEmoji.PLANE, "여행");

    private AhmatdaEmoji emoji;
    private String categoryName;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getLabel() {
        return this.categoryName;
    }
}
