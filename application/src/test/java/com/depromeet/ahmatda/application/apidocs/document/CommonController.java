package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.common.utils.EnumType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommonController {

    @GetMapping("/response")
    public RestResponse<CommonDocsDto> getCommonDocs() {
        return RestResponse.ok(new CommonDocsDto("김민걸", 24));
    }

    @GetMapping("/error-response")
    public ResponseEntity<RestResponse<Object>> getErrorDocs() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RestResponse.error(ErrorCode.UNAUTHORIZED));
    }


    @GetMapping("/common-enum")
    public RestResponse<Object> getEnumDocs() {
        Map<String, String> apiResponseCodes =
                Arrays.stream(ErrorCode.values()).collect(Collectors.toMap(EnumType::getName, EnumType::getLabel));

        return RestResponse.ok(
                EnumDocs.enumDocsBuilder()
                        .errorCodes(apiResponseCodes)
                        .build()
        );
    }

    @AllArgsConstructor
    private static class CommonDocsDto {
        public String name;
        public int age;
    }
}
