package com.depromeet.ahmatda.application.apidocs.document.template;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.template.dto.TemplateItemResponse;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.user.dto.SignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemplateControllerTest extends ApiDocumentationTest {

    @DisplayName("GET: /api/template/user 요청 시 유저가 가지고 있는 해당 카테고리 하위 유저템플릿을 반환한다.")
    @Test
    void getByUserTemplates() throws Exception {
        User userWithDeviceId = User.createUserWithDeviceId(DeviceCode.IOS, "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String deviceId = userWithDeviceId.getDeviceId();
        Long categoryId = 1L;
        List<TemplateItemResponse> items = List.of(
                TemplateItemResponse.builder()
                        .id(1L)
                        .templateId(2L)
                        .categoryId(10L).name("노트북").alarmId(11L).isTake(0L)
                        .build(),
                TemplateItemResponse.builder()
                        .id(2L)
                        .templateId(2L)
                        .categoryId(10L).name("핸드폰").alarmId(10L).isTake(0L)
                        .build());
        List<TemplateResponse> templateResponses = List.of(
                TemplateResponse.builder()
                        .id(100L)
                        .deviceId(deviceId)
                        .templateName("일상에서 중요한거").items(items)
                        .build());

        given(templateService.findByCategoryAndUserId(1L,deviceId)).willReturn(templateResponses);

        mockMvc.perform(get("/api/template/user/{deviceId}?category={categoryId}", deviceId, categoryId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("template-by-user-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("deviceId").description("디바이스ID")
                               )));

    }
}
