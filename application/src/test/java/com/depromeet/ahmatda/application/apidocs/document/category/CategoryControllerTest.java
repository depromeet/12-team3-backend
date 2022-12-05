package com.depromeet.ahmatda.application.apidocs.document.category;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.domain.category.Emoji;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class CategoryControllerTest extends ApiDocumentationTest {

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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
}
