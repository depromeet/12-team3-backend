package com.depromeet.ahmatda.application.apidocs.document.test;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.application.apidocs.document.EnumDocs;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.depromeet.ahmatda.application.apidocs.document.EnumDocs.enumConvertFieldDescriptor;
import static com.depromeet.ahmatda.application.apidocs.snippet.CustomResponseFieldsSnippet.customResponseFields;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommonDocsTest extends ApiDocumentationTest {

    @Test
    void commonDocs() throws Exception {
        ResultActions result = this.mockMvc.perform(
            get("/response")
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentation.document("common-response",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("custom-response", null,
                    attributes(key("title").value("공통 응답")),
                    subsectionWithPath("result").description("데이터"),
                    subsectionWithPath("error").description("에러")
                )
            ));
    }

    @Test
    void commonErrorDocs() throws Exception {
        ResultActions result = this.mockMvc.perform(
                get("/error-response")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isUnauthorized())
            .andDo(MockMvcRestDocumentation.document("common-error-response",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("custom-response", null,
                    attributes(key("title").value("공통 에러 응답")),
                    subsectionWithPath("result").description("데이터"),
                    subsectionWithPath("error").description("에러")
                )
            ));
    }

    @Test
    void commonEnumDocs() throws Exception {
        ResultActions result = this.mockMvc.perform(
                get("/common-enum")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andReturn();
        EnumDocs docs = EnumDocs.getData(mvcResult, objectMapper);

        // then
        result.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentation.document("common-enums",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("custom-response", beneathPath("result.errorCodes").withSubsectionId("errorCodes"),
                    attributes(key("title").value("예외 코드")),
                    enumConvertFieldDescriptor(docs.getErrorCodes())
                )
            ));
    }
}
