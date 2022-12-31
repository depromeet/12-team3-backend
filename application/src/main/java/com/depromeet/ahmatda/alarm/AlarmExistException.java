package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.common.response.ApiException;
import com.depromeet.ahmatda.common.response.ErrorCode;

public class AlarmExistException extends ApiException {
    public AlarmExistException(ErrorCode status) {
        super(status);
    }
}
