package com.depromeet.ahmatda.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class RestResponse<T> {

    private T result;
    private ErrorResponse error;

    private RestResponse(T result) {
        this.result = result;
    }

    private RestResponse(ErrorResponse error) {
        this.error = error;
    }

    public static <T> RestResponse<T> ok() {
        return new RestResponse<>();
    }

    public static <T> RestResponse<T> ok(T result) {
        return new RestResponse<>(result);
    }

    public static <T> RestResponse<T> error(ErrorCode errorCode) {
        return new RestResponse<>(new ErrorResponse(errorCode, null));
    }

    public static <T> RestResponse<T> error(ErrorCode errorCode, Map<String, Object> detail) {
        return new RestResponse<>(new ErrorResponse(errorCode, detail));
    }
}
