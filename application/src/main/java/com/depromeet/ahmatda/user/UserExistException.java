package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;


public class UserExistException extends BusinessException {

    public UserExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
