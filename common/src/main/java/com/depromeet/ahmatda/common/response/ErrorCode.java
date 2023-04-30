package com.depromeet.ahmatda.common.response;

import com.depromeet.ahmatda.common.utils.EnumType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode implements EnumType {
    EXIST_USER("이미 등록된 기기입니다", 400),
    BINDING_ERROR("유효하지 않은 요청값입니다.", 400),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", 500),
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리입니다.", 400),
    USER_NOT_FOUND("존재하지 않는 유저입니다.", 400),
    CATEGORY_AUTHENTICATION_ERROR("유저 인증이 실패했습니다.", 401),
    TEMPLATE_NOT_FOUND("템플릿이 존재하지 않습니다.", 400),
    AUTHENTICATION_ERROR("유저 인증이 실패했습니다.", 401),
    ITEM_NOT_FOUND("소지품이 존재하지 않습니다.", 400),
    ITEM_DUPLICATE("동일한 소지품이 있습니다.", 202),
    ALARM_EXIST("알람이 이미 존재합니다.", 400),
    NOT_IMPLEMENTED("구현되지 않은 기능입니다.", 501);

    private final String desc;
    private final int httpStatus;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getLabel() {
        return desc;
    }
}
