package com.depromeet.ahmatda.application.apidocs.document.template;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.template.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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

        mockMvc.perform(get("/api/template/user?category={categoryId}", categoryId).header(HttpHeader.USER_ID_KEY, userId))
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
                        .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getUserToken()))
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
                                fieldWithPath("items.[].categoryId").type(JsonFieldType.NUMBER).description("소지품의 카테고리 ID"),
                                fieldWithPath("items.[].name").type(JsonFieldType.STRING).description("소지품 이름")
                        )))
                .andDo(print());

    }

    @Test
    void 유저템플릿을_삭제할_수_있다() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        Long templateId = 1L;

        mockMvc.perform(delete("/api/template/{templateId}", templateId)
                .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getUserToken()))
                .andExpect(status().isOk())
                .andDo(document("delete-template",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        pathParameters(
                                parameterWithName("templateId").description("삭제할 유저템플릿 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("error").description("에러")
                        )
                ))
                .andDo(print());
    }

    @Test
    void 유저템플릿의_이름이나_고정여부_수정() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        ModifyTemplateRequest modifyTemplateRequest = ModifyTemplateRequest.builder()
                .templateId(6L)
                .pin(true)
                .templateName("성민의 갓템들")
                .categoryId(2L)
                .build();

        List<TemplateItemResponse> templateItemResponses = List.of(
                TemplateItemResponse.builder()
                        .id(3L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("여권")
                        .alarmId(10L)
                        .isTake(true)
                        .isImportant(false)
                        .build(),
                TemplateItemResponse.builder()
                        .id(4L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("신분증")
                        .alarmId(11L)
                        .isTake(false)
                        .isImportant(true)
                        .build());
        TemplateResponse templateResponse = TemplateResponse.builder()
                .id(6L)
                .templateName("성민의 갓템들")
                .categoryId(2L)
                .pin(true)
                .userToken(userWithDeviceId.getUserToken())
                .items(templateItemResponses)
                .build();


        given(templateService.modfiyTemplateNameAndIsPin(userWithDeviceId.getUserToken(), modifyTemplateRequest)).willReturn(templateResponse);

        String request = objectMapper.writeValueAsString(modifyTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok(templateResponse));

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/template/")
                        .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("modify-template",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("templateId").description("유저템플릿 ID"),
                                fieldWithPath("templateName").description("변경할 유저템플릿 이름"),
                                fieldWithPath("categoryId").description("유저템플릿의 카테고리 ID"),
                                fieldWithPath("pin").description("변경할 유저템플릿 고정여부")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("유저템플릿 ID"),
                                fieldWithPath("result.userToken").type(JsonFieldType.STRING).description("유저템플릿 소유자 ID"),
                                fieldWithPath("result.templateName").type(JsonFieldType.STRING).description("변경된 유저템플릿 ID"),
                                fieldWithPath("result.categoryId").type(JsonFieldType.NUMBER).description("유저템플릿의 카테고리 ID"),
                                fieldWithPath("result.pin").type(JsonFieldType.BOOLEAN).description("유저템플릿의 고정여부"),
                                fieldWithPath("result.items.[].id").type(JsonFieldType.NUMBER).description("유저템플릿의 소지품 ID"),
                                fieldWithPath("result.items.[].name").type(JsonFieldType.STRING).description("유저템플릿의 소지품 명"),
                                fieldWithPath("result.items.[].templateId").type(JsonFieldType.NUMBER).description("유저템플릿의 소지품의 템플릿 ID"),
                                fieldWithPath("result.items.[].categoryId").type(JsonFieldType.NUMBER).description("유저템플릿의 소지품의 카테고리 ID"),
                                fieldWithPath("result.items.[].alarmId").type(JsonFieldType.NUMBER).description("유저템플릿의 소지품 알람 ID"),
                                fieldWithPath("result.items.[].take").type(JsonFieldType.BOOLEAN).description("유저템플릿의 소지품 체크여부"),
                                fieldWithPath("result.items.[].important").type(JsonFieldType.BOOLEAN).description("유저템플릿의 소지품의 중요도 여부"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러")
                        )
                ))
                .andDo(print());
    }

    @Test
    void 유저템플릿의_소지품추가() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateAddItemRequest templateAddItemRequest = TemplateAddItemRequest.builder()
                .itemName("토비의 스프링")
                .templateId(1L)
                .categoryId(10L)
                .important(true)
                .build();

        String request = objectMapper.writeValueAsString(templateAddItemRequest);


        mockMvc.perform(post("/api/template/item")
                        .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(document("template-add-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        requestFields(
                                fieldWithPath("itemName").description("소지품 이름"),
                                fieldWithPath("templateId").description("유저템플릿 ID"),
                                fieldWithPath("categoryId").description("유저템플릿의 카테고리 ID"),
                                fieldWithPath("important").description("소지품 중요체크여부")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("error").description("에러")
                        )
                ))
                .andDo(print());
    }

    @Test
    void 유저템플릿의_소지품단건삭제() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateDeleteItemRequest templateDeleteItemRequest = TemplateDeleteItemRequest.builder()
                .itemId(1L)
                .templateId(6L)
                .build();

        String request = objectMapper.writeValueAsString(templateDeleteItemRequest);

        mockMvc.perform(delete("/api/template/item")
                        .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(document("template-delete-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        requestFields(
                                fieldWithPath("itemId").description("소지품 이름"),
                                fieldWithPath("templateId").description("유저템플릿 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("error").description("에러")
                        )
                ))
                .andDo(print());
    }
}
