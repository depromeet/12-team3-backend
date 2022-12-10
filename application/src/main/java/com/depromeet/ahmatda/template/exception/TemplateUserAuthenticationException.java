package com.depromeet.ahmatda.template.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class TemplateUserAuthenticationException extends BusinessException {
    public TemplateUserAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}