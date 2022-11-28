package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.user.exception.UserExistException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = { UserController.class })
public class UserControllerAdvice {

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<RestResponse<Object>> handleUserExistException(UserExistException e) {
        return ResponseEntity.badRequest().body(RestResponse.error(e.getErrorCode()));
    }
}
