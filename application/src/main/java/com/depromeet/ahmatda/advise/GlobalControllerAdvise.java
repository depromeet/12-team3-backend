package com.depromeet.ahmatda.advise;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.ErrorResponse;
import com.depromeet.ahmatda.common.response.RestResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvise {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(RestResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, Object> details = errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField, FieldError::getDefaultMessage)
                );

        System.out.println(details.get("name"));
        System.out.println(details.get("name"));
        System.out.println(details.get("name"));
        System.out.println(details.get("name"));
        return ResponseEntity.badRequest().body(RestResponse.error(ErrorCode.BINDING_ERROR, details));
    }
}
