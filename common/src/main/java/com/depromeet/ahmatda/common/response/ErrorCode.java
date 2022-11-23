package com.depromeet.ahmatda.common.response;

public enum ErrorCode {
    ;

    final String code;
    final String desc;
    final int status;

    ErrorCode(String code, String desc, int status) {
        this.code = code;
        this.desc = desc;
        this.status = status;
    }
}
