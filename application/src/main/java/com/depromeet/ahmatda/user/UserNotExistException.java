package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.ApiException;
import com.depromeet.ahmatda.common.response.ErrorCode;

public class UserNotExistException extends ApiException {
    public UserNotExistException(ErrorCode status) {
        super(status);
    }
}
