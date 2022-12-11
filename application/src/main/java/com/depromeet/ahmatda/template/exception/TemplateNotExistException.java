package com.depromeet.ahmatda.template.exception;


import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class TemplateNotExistException extends BusinessException {
    public TemplateNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
