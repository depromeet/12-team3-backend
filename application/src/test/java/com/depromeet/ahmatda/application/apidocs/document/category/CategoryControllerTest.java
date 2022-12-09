package com.depromeet.ahmatda.application.apidocs.document.category;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.Emoji;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
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

class CategoryControllerTest extends ApiDocumentationTest {

    private final String TEST_DEVICE_ID = "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0";

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
        User userWithDeviceId = User.createUserWithDeviceId(DeviceCode.IOS, TEST_DEVICE_ID);
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

    @DisplayName("POST: /api/category 요청 시 카테고리가 저장된다")
    @Test
    void createCategory() throws Exception {
        User userWithDeviceId = User.createUserWithDeviceId(DeviceCode.IOS, TEST_DEVICE_ID);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .type("HEALTH").emoji(Emoji.BICEPS).name("CUSTOM")
                .build();
        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_ID_KEY, userWithDeviceId.getDeviceId()))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("category-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-id").description("유저 UUID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("카테고리명"),
                                fieldWithPath("type").description("카테고리 분류"),
                                fieldWithPath("emoji").description("이모지")
                        )))
                .andDo(print());
    }

    @DisplayName("PATCH: /api/category/{id} 요청 시 카테고리를 수정한다")
    @Test
    void modifyCategory() throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("MODIFIED_NAME").emoji(Emoji.AIRPLANE).type("MODIFIED_TYPE")
                .build();
        Category category = Category.builder()
                .id(1L).emoji(Emoji.BICEPS).name("NAME").type("TYPE")
                .build();
        CategoryResponse categoryResponse = CategoryResponse.createByEntity(categoryRequest.modifyEntity(category));
        given(categoryService.modifyCategory(1L, categoryRequest)).willReturn(categoryResponse);

        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok(categoryResponse));

        //when
        ResultActions result = mockMvc.perform(
                patch("/api/category/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("category-modify",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("변경 대상 카테고리의 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("변경할 카테고리명"),
                                fieldWithPath("type").description("변경할 카테고리 타입"),
                                fieldWithPath("emoji").description("변경할 카테고리 이미지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("result.name").type(JsonFieldType.STRING).description("변경된 카테고리명"),
                                fieldWithPath("result.type").type(JsonFieldType.STRING).description("변경된 카테고리 타입"),
                                fieldWithPath("result.emoji").type(JsonFieldType.STRING).description("변경된 카테고리 이모지"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러")
                        )
                ))
                .andDo(print());
    }

    @DisplayName("카테고리 저장 시 ENUM에 존재하지 않는 EMOJI가 들어오면 예외처리한다")
    @Test
    void createCategory_emoji_exception() throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .emoji(Emoji.EXCEPTION).type("CATEGORY_TYPE").name("CATEGORY_NAME")
                .build();
        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("emoji", "일치하는 이모지가 없습니다.")));

        //when
        ResultActions result = mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_ID_KEY, TEST_DEVICE_ID)
        );

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(response))
                .andDo(document("category-emoji-error",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").description("카테고리명"),
                                fieldWithPath("type").description("카테고리 타입"),
                                fieldWithPath("emoji").description("ENUM에 존재하지 않는 이모지 이름")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.NULL).description("결과"),
                                fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러"),
                                fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                                fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메세지"),
                                fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("에러 상세"),
                                fieldWithPath("error.detail.emoji").type(JsonFieldType.STRING).description("에러 상세 메세지")
                        )
                ))
                .andDo(print());
    }

    @DisplayName("카테고리 저장 시 이름에 공백 또는 NULL이 들어오면 예외처리 한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void createCategory_name_valid_exception(String name) throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .type("CATEGORY_TYPE").name(name).emoji(Emoji.AIRPLANE)
                .build();

        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("name", "카테고리 이름은 공백 또는 NULL 일 수 없습니다.")));
        //when
        ResultActions result = mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_ID_KEY, TEST_DEVICE_ID));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(response))
                .andDo(document("category-error-name-null",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").description("NULL 또는 공백의 카테고리 이름"),
                                fieldWithPath("type").description("카테고리 타입"),
                                fieldWithPath("emoji").description("카테고리 이모지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.NULL).description("결과"),
                                fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러"),
                                fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                                fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메세지"),
                                fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("에러 상세"),
                                fieldWithPath("error.detail.name").type(JsonFieldType.STRING).description("에러 상세 메세지")
                        )))
                .andDo(print());
    }

    @DisplayName("카테고리 저장 시 타입에 공백 또는 NULL이 들어오면 예외처리 한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void createCategory_type_valid_exception(String type) throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .type(type).name("CATEGORY_NAME").emoji(Emoji.AIRPLANE)
                .build();

        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("type", "카테고리 타입은 공백 또는 NULL 일 수 없습니다.")));
        //when
        ResultActions result = mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_ID_KEY, TEST_DEVICE_ID));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(response))
                .andDo(document("category-error-type-null",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").description("카테고리 이름"),
                                fieldWithPath("type").description("NULL 또는 공백의 카테고리 타입"),
                                fieldWithPath("emoji").description("카테고리 이모지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.NULL).description("결과"),
                                fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러"),
                                fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                                fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메세지"),
                                fieldWithPath("error.detail").type(JsonFieldType.OBJECT).description("에러 상세"),
                                fieldWithPath("error.detail.type").type(JsonFieldType.STRING).description("에러 상세 메세지")
                        )))
                .andDo(print());
    }
}
