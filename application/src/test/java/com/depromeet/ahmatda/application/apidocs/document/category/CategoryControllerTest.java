package com.depromeet.ahmatda.application.apidocs.document.category;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Emoji;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends ApiDocumentationTest {

    @DisplayName("GET: /api/category/{id} 요청 시 단일 카테고리를 반환한다")
    @Test
    void getCategoryById() throws Exception {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(1L).emoji(Emoji.BICEPS)
                .type("HEALTH").name("HEALTH").build();

        given(categoryService.getCategoryById(1L)).willReturn(categoryResponse);

        mockMvc.perform(get("/api/category/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("category-by-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("카테고리 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("result.name").type(JsonFieldType.STRING).description("카테고리명"),
                                fieldWithPath("result.type").type(JsonFieldType.STRING).description("카테고리 타입"),
                                fieldWithPath("result.emoji").type(JsonFieldType.STRING).description("이모지"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러"))))
                .andDo(print());
    }

    @DisplayName("GET: /api/category/{id} 요청에 존재하지 않는 카테고리 id를 입력하면 예외처리한다")
    @Test
    void getCategoryById_throwException() throws Exception {
        given(categoryService.getCategoryById(99L)).willThrow(new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        mockMvc.perform(get("/api/category/{id}", 99L))
                .andExpect(status().isNotFound())
                .andDo(document("category-by-id-error",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("존재하지 않는 카테고리 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.NULL).description("결과"),
                                fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러"),
                                fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러코드"),
                                fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러메세지"),
                                fieldWithPath("error.detail").type(JsonFieldType.NULL).description("상세")
                        )))
                .andDo(print());
    }

    @DisplayName("GET: /api/category 요청 시 모든 카테고리를 반환한다")
    @Test
    void getCategories() throws Exception {
        List<CategoryResponse> categoryResponses = List.of(
                CategoryResponse.builder()
                        .id(1L).emoji(Emoji.AIRPLANE)
                        .type("DAILY").name("DAILY").build(),
                CategoryResponse.builder()
                        .id(2L).emoji(Emoji.BICEPS)
                        .type("HEALTH").name("HEALTH").build());

        given(categoryService.getCategories()).willReturn(categoryResponses);

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andDo(document("category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.ARRAY).description("결과"),
                                fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("카테고리명"),
                                fieldWithPath("result[].type").type(JsonFieldType.STRING).description("카테고리 타입"),
                                fieldWithPath("result[].emoji").type(JsonFieldType.STRING).description("이모지"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러"))))
                .andDo(print());
    }

    @DisplayName("GET: /api/category/user/{userId} 요청 시 해당 유저의 카테고리 리스트를 반환한다")
    @Test
    void getCategoriesByUserId() throws Exception {
        User userWithDeviceId = User.createUserWithDeviceId(DeviceCode.IOS, "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userId = userWithDeviceId.getDeviceId();

        List<CategoryResponse> categoryResponses = List.of(
                CategoryResponse.builder()
                        .id(1L).emoji(Emoji.AIRPLANE)
                        .type("DAILY").name("DAILY").build(),
                CategoryResponse.builder()
                        .id(2L).emoji(Emoji.BICEPS)
                        .type("HEALTH").name("HEALTH").build());

        given(categoryService.getCategoriesByUser(userId)).willReturn(categoryResponses);

        mockMvc.perform(get("/api/category/user")
                        .header(HttpHeader.USER_ID_KEY, userId))
                .andExpect(status().isOk())
                .andDo(document("category-by-user",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.ARRAY).description("결과"),
                                fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("카테고리명"),
                                fieldWithPath("result[].type").type(JsonFieldType.STRING).description("카테고리 타입"),
                                fieldWithPath("result[].emoji").type(JsonFieldType.STRING).description("이모지"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러"))))
                .andDo(print());
    }
}
