package com.depromeet.ahmatda.recommend;

import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.recommend.exception.RecommendNotExistException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = { RecommendController.class })
public class RecommendControllerAdvice {

    @ExceptionHandler(value = RecommendNotExistException.class)
    public ResponseEntity<RestResponse<Object>> handlerRecommendNotExistException(RecommendNotExistException e) {
        return ResponseEntity.badRequest().body(RestResponse.error(e.getErrorCode()));
    }
}
