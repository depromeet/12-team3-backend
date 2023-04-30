package com.depromeet.ahmatda.template.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class TemplateItemException extends BusinessException {
    public TemplateItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
