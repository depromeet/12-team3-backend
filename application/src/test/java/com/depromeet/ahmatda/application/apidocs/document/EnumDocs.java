package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.common.utils.EnumType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@Getter
@Builder(builderClassName = "EnumDocsBuilder", builderMethodName = "enumDocsBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {

    Map<String, String> errorCodes;
    Map<String, String> userRegisterCode;
    Map<String, String> onBoardingCategory;

    public static EnumDocs getEnumDocs() {
        return enumDocsBuilder()
                .errorCodes(getMapForEnumDocs(ErrorCode.values()))
                .build();
    }

    private static Map<String, String> getMapForEnumDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes).collect(Collectors.toMap(EnumType::getName, EnumType::getLabel));
    }

    public static EnumDocs getData(MvcResult result, ObjectMapper objectMapper) throws IOException {
        RestResponse<EnumDocs> responseDto = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), new TypeReference<>() {}
        );
        return responseDto.getResult();
    }

    public static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
