package com.depromeet.ahmatda.application.apidocs.document.template;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemplateControllerTest extends ApiDocumentationTest {

    @DisplayName("GET: /api/template/user ?????? ??? ????????? ????????? ?????? ?????? ???????????? ?????? ?????????????????? ????????????.")
    @Test
    void getByUserTemplates() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userId = userWithDeviceId.getUserToken();
        Long categoryId = 1L;
        List<TemplateItemResponse> items = List.of(
                TemplateItemResponse.builder()
                        .id(1L)
                        .templateId(2L)
                        .categoryId(1L).name("?????????").isTake(false)
                        .build(),
                TemplateItemResponse.builder()
                        .id(2L)
                        .templateId(2L)
                        .categoryId(1L).name("?????????").isTake(false)
                        .build());
        List<TemplateResponse> templateResponses = List.of(
                TemplateResponse.builder()
                        .id(100L)
                        .userToken(userId)
                        .alarmInfo("2022-12-31 12??? 00??? 10??? ???")
                        .categoryId(1L)
                        .templateName("???????????? ????????????").items(items)
                        .build());

        given(templateService.findByCategoryAndUserId(1L,userId)).willReturn(templateResponses);

        mockMvc.perform(get("/api/template/user?category={categoryId}", categoryId).header(HttpHeader.USER_TOKEN, userId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("template-by-user-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        )));

    }

    @DisplayName("POST: /api/template ?????? ??? ?????????????????? ????????????.")
    @Test
    void createUserTemplate() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        List<TemplateItemRequest> templateItemRequests = List.of(
                TemplateItemRequest.builder()
                        .categoryId(1L)
                        .name("??????")
                        .build(),
                TemplateItemRequest.builder()
                        .categoryId(1L)
                        .name("?????????")
                        .build());
        CreateTemplateRequest createTemplateRequest = CreateTemplateRequest.builder()
                .templateName("????????????!")
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
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        ),
                        requestFields(
                                fieldWithPath("templateName").description("??????????????? ??????"),
                                fieldWithPath("categoryId").description("???????????? ID"),
                                fieldWithPath("items.[].categoryId").type(JsonFieldType.NUMBER).description("???????????? ???????????? ID"),
                                fieldWithPath("items.[].name").type(JsonFieldType.STRING).description("????????? ??????")
                        )))
                .andDo(print());

    }

    @Test
    void ??????????????????_?????????_???_??????() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        Long templateId = 1L;

        mockMvc.perform(delete("/api/template/{templateId}", templateId)
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken()))
                .andExpect(status().isOk())
                .andDo(document("delete-template",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        ),
                        pathParameters(
                                parameterWithName("templateId").description("????????? ??????????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("result").description("??????"),
                                fieldWithPath("error").description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ???????????????_????????????_??????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        ModifyTemplateRequest modifyTemplateRequest = ModifyTemplateRequest.builder()
                .templateId(6L)
                .categoryId(2L)
                .pin(true)
                .build();

        List<TemplateItemResponse> templateItemResponses = List.of(
                TemplateItemResponse.builder()
                        .id(3L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("??????")
                        .isTake(true)
                        .isImportant(false)
                        .build(),
                TemplateItemResponse.builder()
                        .id(4L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("?????????")
                        .isTake(false)
                        .isImportant(true)
                        .build());
        TemplateResponse templateResponse = TemplateResponse.builder()
                .id(6L)
                .templateName("????????? ?????????")
                .categoryId(2L)
                .pin(true)
                .userToken(userWithDeviceId.getUserToken())
                .alarmInfo(null)
                .items(templateItemResponses)
                .build();


        given(templateService.modfiyTemplateNameAndIsPin(userWithDeviceId.getUserToken(), modifyTemplateRequest)).willReturn(templateResponse);

        String request = objectMapper.writeValueAsString(modifyTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok(templateResponse));

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/template/")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("modify-template-pin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("templateId").description("??????????????? ID"),
                                fieldWithPath("templateName").description("????????? ??????????????? ??????").ignored(),
                                fieldWithPath("categoryId").description("?????????????????? ???????????? ID"),
                                fieldWithPath("pin").description("????????? ??????????????? ????????????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("??????"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("??????????????? ID"),
                                fieldWithPath("result.userToken").type(JsonFieldType.STRING).description("??????????????? ????????? ID"),
                                fieldWithPath("result.templateName").type(JsonFieldType.STRING).description("????????? ??????????????? ID"),
                                fieldWithPath("result.alarmInfo").type(JsonFieldType.NULL).description("????????? ?????? ???????????? (?????? Null)"),
                                fieldWithPath("result.categoryId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ID"),
                                fieldWithPath("result.pin").type(JsonFieldType.BOOLEAN).description("?????????????????? ????????????"),
                                fieldWithPath("result.items.[].id").type(JsonFieldType.NUMBER).description("?????????????????? ????????? ID"),
                                fieldWithPath("result.items.[].name").type(JsonFieldType.STRING).description("?????????????????? ????????? ???"),
                                fieldWithPath("result.items.[].templateId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ????????? ID"),
                                fieldWithPath("result.items.[].categoryId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ???????????? ID"),
                                fieldWithPath("result.items.[].take").type(JsonFieldType.BOOLEAN).description("?????????????????? ????????? ????????????"),
                                fieldWithPath("result.items.[].important").type(JsonFieldType.BOOLEAN).description("?????????????????? ???????????? ????????? ??????"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ???????????????_??????_??????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        ModifyTemplateRequest modifyTemplateRequest = ModifyTemplateRequest.builder()
                .templateId(6L)
                .templateName("????????? ?????????")
                .categoryId(2L)
                .build();

        List<TemplateItemResponse> templateItemResponses = List.of(
                TemplateItemResponse.builder()
                        .id(3L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("??????")
                        .isTake(true)
                        .isImportant(false)
                        .build(),
                TemplateItemResponse.builder()
                        .id(4L)
                        .categoryId(2L)
                        .templateId(6L)
                        .name("?????????")
                        .isTake(false)
                        .isImportant(true)
                        .build());
        TemplateResponse templateResponse = TemplateResponse.builder()
                .id(6L)
                .templateName("????????? ?????????")
                .categoryId(2L)
                .pin(true)
                .alarmInfo("")
                .userToken(userWithDeviceId.getUserToken())
                .items(templateItemResponses)
                .build();


        given(templateService.modfiyTemplateNameAndIsPin(userWithDeviceId.getUserToken(), modifyTemplateRequest)).willReturn(templateResponse);

        String request = objectMapper.writeValueAsString(modifyTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok(templateResponse));

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/template/")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("modify-template-name",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("templateId").description("??????????????? ID"),
                                fieldWithPath("templateName").description("????????? ??????????????? ??????"),
                                fieldWithPath("categoryId").description("?????????????????? ???????????? ID"),
                                fieldWithPath("pin").description("????????? ??????????????? ????????????").ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("??????"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("??????????????? ID"),
                                fieldWithPath("result.userToken").type(JsonFieldType.STRING).description("??????????????? ????????? ID"),
                                fieldWithPath("result.templateName").type(JsonFieldType.STRING).description("????????? ??????????????? ID"),
                                fieldWithPath("result.categoryId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ID"),
                                fieldWithPath("result.pin").type(JsonFieldType.BOOLEAN).description("?????????????????? ????????????"),
                                fieldWithPath("result.alarmInfo").type(JsonFieldType.STRING).description("?????? ????????? ??? ?????? null"),
                                fieldWithPath("result.items.[].id").type(JsonFieldType.NUMBER).description("?????????????????? ????????? ID"),
                                fieldWithPath("result.items.[].name").type(JsonFieldType.STRING).description("?????????????????? ????????? ???"),
                                fieldWithPath("result.items.[].templateId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ????????? ID"),
                                fieldWithPath("result.items.[].categoryId").type(JsonFieldType.NUMBER).description("?????????????????? ???????????? ???????????? ID"),
                                fieldWithPath("result.items.[].take").type(JsonFieldType.BOOLEAN).description("?????????????????? ????????? ????????????"),
                                fieldWithPath("result.items.[].important").type(JsonFieldType.BOOLEAN).description("?????????????????? ???????????? ????????? ??????"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ??????????????????_???????????????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateAddItemRequest templateAddItemRequest = TemplateAddItemRequest.builder()
                .itemName("????????? ?????????")
                .templateId(1L)
                .categoryId(10L)
                .important(true)
                .build();

        String request = objectMapper.writeValueAsString(templateAddItemRequest);


        mockMvc.perform(post("/api/template/item")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(document("template-add-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        ),
                        requestFields(
                                fieldWithPath("itemName").description("????????? ??????"),
                                fieldWithPath("templateId").description("??????????????? ID"),
                                fieldWithPath("categoryId").description("?????????????????? ???????????? ID"),
                                fieldWithPath("important").description("????????? ??????????????????")
                        ),
                        responseFields(
                                fieldWithPath("result").description("??????"),
                                fieldWithPath("error").description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ??????????????????_?????????????????????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateDeleteItemRequest templateDeleteItemRequest = TemplateDeleteItemRequest.builder()
                .itemId(1L)
                .templateId(6L)
                .build();

        String request = objectMapper.writeValueAsString(templateDeleteItemRequest);

        mockMvc.perform(delete("/api/template/item")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(document("template-delete-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        ),
                        requestFields(
                                fieldWithPath("itemId").description("????????? ID"),
                                fieldWithPath("templateId").description("??????????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("result").description("??????"),
                                fieldWithPath("error").description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ??????????????????_???????????????_??????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateItemModfiyRequest templateItemModfiyRequest = TemplateItemModfiyRequest.builder()
                .templateId(1L)
                .itemId(6L)
                .isTake(true)
                .build();

        String request = objectMapper.writeValueAsString(templateItemModfiyRequest);

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/template/item")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("template-modfiy-pin-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("itemId").description("????????? ID"),
                                fieldWithPath("templateId").description("??????????????? ID"),
                                fieldWithPath("isTake").description("????????? ????????????"),
                                fieldWithPath("modifiedItemName").description("????????? ????????? ??????").ignored(),
                                fieldWithPath("important").description("????????? ??????????????????").ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").description("??????"),
                                fieldWithPath("error").description("??????")
                        )
                ))
                .andDo(print());
    }

    @Test
    void ??????????????????_?????????_??????() throws Exception {
        //given
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");

        TemplateItemModfiyRequest templateItemModfiyRequest = TemplateItemModfiyRequest.builder()
                .templateId(1L)
                .itemId(6L)
                .modifiedItemName("????????????")
                .important(true)
                .build();

        String request = objectMapper.writeValueAsString(templateItemModfiyRequest);

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/template/item")
                        .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("template-modfiy-item",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("itemId").description("????????? ID"),
                                fieldWithPath("templateId").description("??????????????? ID"),
                                fieldWithPath("modifiedItemName").description("????????? ????????? ??????"),
                                fieldWithPath("important").description("????????? ??????????????????"),
                                fieldWithPath("isTake").description("????????? ????????????").ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").description("??????"),
                                fieldWithPath("error").description("??????")
                        )
                ))
                .andDo(print());
    }

}