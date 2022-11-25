package com.depromeet.ahmatda.common.response;

import com.depromeet.ahmatda.common.utils.EnumType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode implements EnumType {
    UNAUTHORIZED("인증에 실패하였습니다.");

    private final String desc;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getLabel() {
        return desc;
    }
}
