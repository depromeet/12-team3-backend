package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class UserNotExistException extends BusinessException {
    public UserNotExistException(ErrorCode status) {
        super(status);
    }
}
