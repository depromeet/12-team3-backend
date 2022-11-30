package com.depromeet.ahmatda.category;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import com.depromeet.ahmatda.domain.category.repository.CategoryRepository;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryAdaptor categoryAdaptor;

    @Autowired
    UserAdaptor userAdaptor;

    @DisplayName("GET: /api/category 요청 시 모든 카테고리를 반환한다")
    @Test
    void getCategories() throws Exception {
        mockMvc.perform(get("/api/category"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(document("category",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.ARRAY).description("카테고리"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("에러"))));
    }
}
