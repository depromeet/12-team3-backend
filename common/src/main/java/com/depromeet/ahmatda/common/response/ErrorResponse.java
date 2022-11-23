package com.depromeet.ahmatda.common.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;

    ErrorResponse(ErrorCode exceptionCd) {
        this.status = exceptionCd.status;
        this.code = exceptionCd.code;
        this.message = exceptionCd.desc;
    }
}
