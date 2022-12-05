package com.depromeet.ahmatda.category.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class CategoryNotExistException extends BusinessException {

    public CategoryNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
