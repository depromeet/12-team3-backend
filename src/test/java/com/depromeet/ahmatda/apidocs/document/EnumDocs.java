package com.depromeet.ahmatda.apidocs.document;

import com.depromeet.ahmatda.utils.EnumType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@NoArgsConstructor
public class EnumDocs {

    // TODO : 아래 세가지 주석은 문서화가 필요한 Enum 을 정의하는 곳.
//    Map<String, String> colors;

    @Builder(builderClassName = "EnumDocsBuilder", builderMethodName = "enumDocsBuilder")
    public EnumDocs(Map<String, String> colors) {
//        this.colors = colors;
    }

    public static EnumDocs getEnumDocs() {
        return enumDocsBuilder()
//                .colors(getMapForEnumDocs(Color.values()))
                .build();
    }

    private static Map<String, String> getMapForEnumDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes).collect(Collectors.toMap(EnumType::getName, EnumType::getLabel));
    }

    // TODO : Common 한 RestApi 의 Docs 와 Enum 의 Docs 를 한 테스트에 정의할 것이다. 해서, RestApi 요청, 응답 형태가 정해지면 수정한다.
//    static EnumDocs getData(MvcResult result, ObjectMapper objectMapper) throws IOException {
//        DocsCommonController.ApiResponseDto<EnumDocs> responseDto =
//                objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<>() {});
//        return responseDto.getData();
//    }

    static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
