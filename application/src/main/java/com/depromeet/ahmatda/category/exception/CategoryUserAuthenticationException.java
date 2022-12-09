package com.depromeet.ahmatda.category.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class CategoryUserAuthenticationException extends BusinessException {

    public CategoryUserAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
