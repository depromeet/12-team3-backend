package com.depromeet.ahmatda.user.exception;

import com.depromeet.ahmatda.exception.BusinessException;

public class UserNotExistException extends BusinessException {

    public UserNotExistException(String message) {
        super(message);
    }
}
