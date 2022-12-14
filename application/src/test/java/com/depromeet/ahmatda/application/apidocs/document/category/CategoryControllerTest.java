package com.depromeet.ahmatda.application.apidocs.document.category;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.category.exception.CategoryUserAuthenticationException;
import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.user.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class CategoryControllerTest extends ApiDocumentationTest {

    private final String TEST_USER_TOKEN = UUID.randomUUID().toString();

    @DisplayName("GET: /api/category/{id} ?????? ??? ?????? ??????????????? ????????????")
    @Test
    void getCategoryById() throws Exception {
        CategoryResponse categoryResponse = CategoryResponse.builder()
            .id(1L).emoji(AhmatdaEmoji.BUS)
            .type(CategoryType.EXERCISE).name("HEALTH").build();

        given(categoryService.getCategoryById(1L)).willReturn(categoryResponse);

        mockMvc.perform(get("/api/category/{id}", 1L))
            .andExpect(status().isOk())
            .andDo(document("category-by-id",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("id").description("???????????? ID")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("???????????? ID"),
                    fieldWithPath("result.name").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("result.type").type(JsonFieldType.STRING).description("???????????? ??????"),
                    fieldWithPath("result.emoji").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("??????"))))
            .andDo(print());
    }

    @DisplayName("GET: /api/category/{id} ????????? ???????????? ?????? ???????????? id??? ???????????? ??????????????????")
    @Test
    void getCategoryById_throwException() throws Exception {
        given(categoryService.getCategoryById(99L)).willThrow(
            new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        mockMvc.perform(get("/api/category/{id}", 99L))
            .andExpect(status().isNotFound())
            .andDo(document("category-by-id-error",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("id").description("???????????? ?????? ???????????? ID")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.NULL).description("??????")
                )))
            .andDo(print());
    }

    @DisplayName("GET: /api/category ?????? ??? ?????? ??????????????? ????????????")
    @Test
    void getCategories() throws Exception {
        List<CategoryResponse> categoryResponses = List.of(
            CategoryResponse.builder()
                .id(1L).emoji(AhmatdaEmoji.BOWLING)
                .type(CategoryType.DAILY).name("DAILY").build(),
            CategoryResponse.builder()
                .id(2L).emoji(AhmatdaEmoji.EMPTY_CARD)
                .type(CategoryType.EXERCISE).name("HEALTH").build());

        given(categoryService.getCategories()).willReturn(categoryResponses);

        mockMvc.perform(get("/api/category"))
            .andExpect(status().isOk())
            .andDo(document("category",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.ARRAY).description("??????"),
                    fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("???????????? ID"),
                    fieldWithPath("result[].name").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("result[].type").type(JsonFieldType.STRING)
                        .description("???????????? ??????"),
                    fieldWithPath("result[].emoji").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("??????"))))
            .andDo(print());
    }

    @DisplayName("GET: /api/category/user/{userToken} ?????? ??? ?????? ????????? ???????????? ???????????? ????????????")
    @Test
    void getCategoriesByUserToken() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken(TEST_USER_TOKEN);
        String userToken = userWithDeviceId.getUserToken();

        List<CategoryResponse> categoryResponses = List.of(
            CategoryResponse.builder()
                .id(1L).emoji(AhmatdaEmoji.GYM)
                .type(CategoryType.DAILY).name("DAILY").build(),
            CategoryResponse.builder()
                .id(2L).emoji(AhmatdaEmoji.PLANE)
                .type(CategoryType.TRAVEL).name("HEALTH").build());

        given(categoryService.getCategoriesByUser(userToken)).willReturn(categoryResponses);

        mockMvc.perform(get("/api/category/user")
                .header(HttpHeader.USER_TOKEN, userToken))
            .andExpect(status().isOk())
            .andDo(document("category-by-user",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description("?????? UUID")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.ARRAY).description("??????"),
                    fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("???????????? ID"),
                    fieldWithPath("result[].name").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("result[].type").type(JsonFieldType.STRING)
                        .description("???????????? ??????"),
                    fieldWithPath("result[].emoji").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("??????"))))
            .andDo(print());
    }

    @DisplayName("POST: /api/category ?????? ??? ??????????????? ????????????")
    @Test
    void createCategory() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken(TEST_USER_TOKEN);
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .type(CategoryType.EXERCISE).emoji(AhmatdaEmoji.TUBE).name("CUSTOM")
            .build();
        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(HttpHeader.USER_TOKEN, userWithDeviceId.getUserToken()))
            .andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(document("category-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description("?????? UUID")
                ),
                requestFields(
                    fieldWithPath("name").description("???????????????"),
                    fieldWithPath("type").description("???????????? ??????"),
                    fieldWithPath("emoji").description("?????????")
                )))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ????????? 10????????? ???????????? ????????????")
    @Test
    void createCategory_name_length_exception() throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .name("?????????????????????????????????").type(CategoryType.DAILY).emoji(AhmatdaEmoji.CAMERA)
            .build();

        String request = objectMapper.writeValueAsString(categoryRequest);

        //when
        ResultActions result = mockMvc.perform(post("/api/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request));

        //then
        result.andExpect(status().isBadRequest())
            .andDo(document("category-name-length-error",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("name").description("??? ????????? ????????? ???????????? ??????"),
                    fieldWithPath("type").description("???????????? ??????"),
                    fieldWithPath("emoji").description("???????????? ?????????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("?????? ??????"),
                    fieldWithPath("error.detail.name").type(JsonFieldType.STRING)
                        .description("?????? ?????? ?????????")
                )))
            .andDo(print());
    }

    @DisplayName("PATCH: /api/category/{id} ?????? ??? ??????????????? ????????????")
    @Test
    void modifyCategory() throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .name("MODIFIED_NAME").emoji(AhmatdaEmoji.FRIENDS).type(CategoryType.TRAVEL)
            .build();
        Category category = Category.builder()
            .id(1L).emoji(AhmatdaEmoji.RUN).name("NAME").type(CategoryType.EXERCISE)
            .build();
        CategoryResponse categoryResponse = CategoryResponse.createByEntity(
            categoryRequest.modifyEntity(category));
        given(categoryService.modifyCategory(TEST_USER_TOKEN, 1L, categoryRequest)).willReturn(
            categoryResponse);

        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok(categoryResponse));

        //when
        ResultActions result = mockMvc.perform(
            patch("/api/category/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN));

        //then
        result.andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(document("category-modify",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description("?????? ????????? ?????? UUID")
                ),
                pathParameters(
                    parameterWithName("id").description("?????? ?????? ??????????????? ID")
                ),
                requestFields(
                    fieldWithPath("name").description("????????? ???????????????"),
                    fieldWithPath("type").description("????????? ???????????? ??????"),
                    fieldWithPath("emoji").description("????????? ???????????? ?????????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("???????????? ID"),
                    fieldWithPath("result.name").type(JsonFieldType.STRING)
                        .description("????????? ???????????????"),
                    fieldWithPath("result.type").type(JsonFieldType.STRING)
                        .description("????????? ???????????? ??????"),
                    fieldWithPath("result.emoji").type(JsonFieldType.STRING)
                        .description("????????? ???????????? ?????????"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("??????")
                )
            ))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ???????????? ????????? ????????? ?????? ?????? ?????? ????????????")
    @Test
    void modifyCategory_authentication_exception() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .name("NAME").type(CategoryType.EXERCISE).emoji(AhmatdaEmoji.CAMERA)
            .build();

        given(categoryService.modifyCategory(TEST_USER_TOKEN, 99L, categoryRequest))
            .willThrow(new CategoryUserAuthenticationException(ErrorCode.CATEGORY_AUTHENTICATION_ERROR));

        String request = objectMapper.writeValueAsString(categoryRequest);

        mockMvc.perform(
                patch("/api/category/{categoryId}", 99L)
                    .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isUnauthorized())
            .andDo(document("category-modify-error",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description(
                        "??????????????? ????????? ????????? ?????? ?????? ?????? UUID??? ?????? ??????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.NULL).description("??????")
                )))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ENUM??? ???????????? ?????? EMOJI??? ???????????? ??????????????????")
    @Test
    void createCategory_emoji_exception() throws Exception {
        //given
        String request = "{\"name\":\"NAME\",\"type\":\"DAILY\",\"emoji\":\"ENUM??????????????????\"}";
        String response = objectMapper.writeValueAsString(
            RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("emoji", "???????????? ???????????? ????????????.")));

        //when
        ResultActions result = mockMvc.perform(
            post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN)
        );

        //then
        result.andExpect(status().isBadRequest())
            .andExpect(content().string(response))
            .andDo(document("category-emoji-error",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("name").description("???????????????"),
                    fieldWithPath("type").description("???????????? ??????"),
                    fieldWithPath("emoji").description("ENUM??? ???????????? ?????? ????????? ??????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("?????? ??????"),
                    fieldWithPath("error.detail.emoji").type(JsonFieldType.STRING)
                        .description("?????? ?????? ?????????")
                )
            ))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ????????? ?????? ?????? NULL??? ???????????? ???????????? ??????")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void createCategory_name_valid_exception(String name) throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .type(CategoryType.EXERCISE).name(name).emoji(AhmatdaEmoji.WORK)
            .build();

        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(
            RestResponse.error(ErrorCode.BINDING_ERROR,
                Map.of("name", "???????????? ????????? ?????? ?????? NULL ??? ??? ????????????.")));
        //when
        ResultActions result = mockMvc.perform(
            post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN));

        //then
        result.andExpect(status().isBadRequest())
            .andExpect(content().string(response))
            .andDo(document("category-error-name-null",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("name").description("NULL ?????? ????????? ???????????? ??????"),
                    fieldWithPath("type").description("???????????? ??????"),
                    fieldWithPath("emoji").description("???????????? ?????????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("?????? ??????"),
                    fieldWithPath("error.detail.name").type(JsonFieldType.STRING)
                        .description("?????? ?????? ?????????")
                )))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ????????? ENUM??? ???????????? ?????? ?????? ?????? ???????????? ????????????")
    @Test
    void createCategory_type_valid_exception() throws Exception {
        String request = "{\"name\":\"NAME\",\"type\":\"ENUM???????????????????????????\",\"emoji\":\"RUN\"}";
        String response = objectMapper.writeValueAsString(
            RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("type", "???????????? ???????????? ????????? ????????????.")));

        //when
        ResultActions result = mockMvc.perform(
            post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN));

        //then
        result.andExpect(status().isBadRequest())
            .andExpect(content().string(response))
            .andDo(document("category-error-type-null",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("name").description("???????????? ??????"),
                    fieldWithPath("type").description("NULL ?????? ????????? ???????????? ??????"),
                    fieldWithPath("emoji").description("???????????? ?????????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("?????? ??????"),
                    fieldWithPath("error.detail.type").type(JsonFieldType.STRING)
                        .description("?????? ?????? ?????????")
                )))
            .andDo(print());
    }

    @DisplayName("DELETE: /api/category/{id} ?????? ??? ??????????????? ????????????.")
    @Test
    void removeCategory() throws Exception {
        mockMvc.perform(
                delete("/api/category/{categoryId}", 1L)
                    .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN))
            .andExpect(status().isOk())
            .andDo(document("category-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description("?????? UUID")
                ),
                pathParameters(
                    parameterWithName("categoryId").description("????????? ???????????? ID")
                ),
                responseFields(
                    fieldWithPath("result").description("??????"),
                    fieldWithPath("error").description("??????")
                )
            ))
            .andDo(print());
    }

    @DisplayName("???????????? ?????? ??? ???????????? ????????? ????????? ?????? ?????? ?????? ????????????")
    @Test
    void removeCategory_authentication_exception() throws Exception {
        doThrow(new CategoryUserAuthenticationException(ErrorCode.CATEGORY_AUTHENTICATION_ERROR))
            .when(categoryService).removeCategory(TEST_USER_TOKEN, 99L);

        mockMvc.perform(delete("/api/category/{categoryId}", 99L)
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN))
            .andExpect(status().isUnauthorized())
            .andDo(document("category-delete-error",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description(
                        "??????????????? ????????? ????????? ?????? ?????? ?????? UUID??? ?????? ??????")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.NULL).description("??????"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("??????"),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("error.detail").type(JsonFieldType.NULL).description("??????")
                )))
            .andDo(print());
    }

}
