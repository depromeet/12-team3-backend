package com.depromeet.ahmatda.category;

import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.category.exception.CategoryUserAuthenticationException;
import com.depromeet.ahmatda.common.response.RestResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(CategoryNotExistException.class)
    public ResponseEntity<RestResponse<Object>> handleCategoryNotExistsException(CategoryNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.error(exception.getErrorCode()));
    }

    @ExceptionHandler(CategoryUserAuthenticationException.class)
    public ResponseEntity<RestResponse<Object>> handleCategoryUserAuthenticationException(CategoryUserAuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RestResponse.error(exception.getErrorCode()));
    }
}
