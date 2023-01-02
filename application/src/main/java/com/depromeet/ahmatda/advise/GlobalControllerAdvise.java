package com.depromeet.ahmatda.advise;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.ErrorResponse;
import com.depromeet.ahmatda.common.response.RestResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.depromeet.ahmatda.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvise {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(RestResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestResponse<ErrorResponse>> handleBusinessException(BusinessException e) {
        return new ResponseEntity(
            RestResponse.error(e.getErrorCode()),
            HttpStatus.valueOf(e.getErrorCode().getHttpStatus())
        );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return new ResponseEntity(
                RestResponse.error(ErrorCode.NOT_IMPLEMENTED),
                HttpStatus.NOT_IMPLEMENTED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, Object> details = errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField, FieldError::getDefaultMessage)
                );

        return ResponseEntity.badRequest().body(RestResponse.error(ErrorCode.BINDING_ERROR, details));
    }
}
