package com.depromeet.ahmatda.recommend.exception;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.exception.BusinessException;

public class RecommendNotExistException extends BusinessException {

    public RecommendNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
