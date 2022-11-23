package com.depromeet.ahmatda.common.response;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode status;
    private final String message;

    public ApiException(ErrorCode status, Exception e) {
        super(e);
        this.status = status;
        this.message = status.getDesc();
    }

    public ApiException(ErrorCode status, String message, Exception e) {
        super(e);
        this.status = status;
        this.message = message;
    }

    public ApiException(ErrorCode status) {
        this.status = status;
        this.message = status.getDesc();
    }

    public ApiException(ErrorCode status, String message) {
        this.status = status;
        this.message = message;
    }
}
