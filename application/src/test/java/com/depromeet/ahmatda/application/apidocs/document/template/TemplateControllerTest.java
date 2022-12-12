package com.depromeet.ahmatda.application.apidocs.document.template;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemResponse;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemplateControllerTest extends ApiDocumentationTest {

    @DisplayName("GET: /api/template/user 요청 시 유저가 가지고 있는 해당 카테고리 하위 유저템플릿을 반환한다.")
    @Test
    void getByUserTemplates() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userId = userWithDeviceId.getUserToken();
        Long categoryId = 1L;
        List<TemplateItemResponse> items = List.of(
                TemplateItemResponse.builder()
                        .id(1L)
                        .templateId(2L)
                        .categoryId(1L).name("노트북").alarmId(11L).isTake(false)
                        .build(),
                TemplateItemResponse.builder()
                        .id(2L)
                        .templateId(2L)
                        .categoryId(1L).name("핸드폰").alarmId(10L).isTake(false)
                        .build());
        List<TemplateResponse> templateResponses = List.of(
                TemplateResponse.builder()
                        .id(100L)
                        .userToken(userId)
                        .categoryId(1L)
                        .templateName("일상에서 중요한거").items(items)
                        .build());

        given(templateService.findByCategoryAndUserId(1L,userId)).willReturn(templateResponses);

        mockMvc.perform(get("/api/template/user?category={categoryId}", categoryId).header(HttpHeader.USER_TOKEN, userId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("template-by-user-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        )));

    }

    @DisplayName("POST: /api/template 요청 시 유저템플릿이 저장된다.")
    @Test
    void createUserTemplate() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        List<TemplateItemRequest> templateItemRequests = List.of(
                TemplateItemRequest.builder()
                        .categoryId(1L)
                        .name("여권")
                        .build(),
                TemplateItemRequest.builder()
                        .categoryId(1L)
                        .name("신분증")
                        .build());
        CreateTemplateRequest createTemplateRequest = CreateTemplateRequest.builder()
                .templateName("중요한거!")
                .categoryId(1L)
                .items(templateItemRequests)
                .build();
        String request = objectMapper.writeValueAsString(createTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken()))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("create-template",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        requestFields(
                                fieldWithPath("templateName").description("유저템플릿 이름"),
                                fieldWithPath("categoryId").description("카테고리 ID"),
                                //TODO: 형식 수정필요 items 안에 안보임
                                fieldWithPath("items.[].categoryId").type(JsonFieldType.NUMBER).description("소지품의 카테고리 ID"),
                                fieldWithPath("items.[].name").type(JsonFieldType.STRING).description("소지품 이름")
                        )))
                .andDo(print());

    }
}
