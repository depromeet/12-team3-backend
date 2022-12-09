package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = { UserController.class })
public class UserControllerAdvice {

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(RestResponse.error(ErrorCode.BINDING_ERROR));
    }
}
