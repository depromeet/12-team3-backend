package com.depromeet.ahmatda.template;

import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = { TemplateController.class })
public class TemplateControllerAdvice {

    @ExceptionHandler(value = TemplateNotExistException.class)
    public ResponseEntity<RestResponse<Object>> handlerTemplateNotExistException(TemplateNotExistException e) {
        return ResponseEntity.badRequest().body(RestResponse.error(e.getErrorCode()));
    }
}
