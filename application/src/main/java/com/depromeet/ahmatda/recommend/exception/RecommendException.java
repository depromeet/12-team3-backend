package com.depromeet.ahmatda.recommend.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class RecommendException extends BusinessException {

    public RecommendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
