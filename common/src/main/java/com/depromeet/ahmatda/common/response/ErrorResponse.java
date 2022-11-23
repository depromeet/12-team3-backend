package com.depromeet.ahmatda.common.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, Object> detail;

    ErrorResponse(ErrorCode exceptionCd, Map<String, Object> detail) {
        this.code = exceptionCd.name();
        this.message = exceptionCd.getDesc();
        this.detail = detail;
    }
}
