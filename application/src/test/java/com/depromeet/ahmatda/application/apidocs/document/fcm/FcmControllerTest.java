package com.depromeet.ahmatda.application.apidocs.document.fcm;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;

public class FcmControllerTest extends ApiDocumentationTest {

    @DisplayName("유저 FCM Token 갱신")
    @Test
    void updateFcmToken() throws Exception {
        FcmTokenRequest fcmTokenRequest = new FcmTokenRequest("TEST-TOKEN");
        String request = objectMapper.writeValueAsString(fcmTokenRequest);

        mockMvc.perform(post("/api/user/token")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeader.USER_TOKEN, UUID.randomUUID().toString())
                .content(request))
            .andExpect(status().isOk())
            .andDo(MockMvcRestDocumentation.document("fcm-token-update",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("ahmatda-user-token").description("유저 UUID")
                ),
                requestFields(
                    fieldWithPath("fcmToken").type(JsonFieldType.STRING).description("유저 FCM Token 갱신")
                )))
            .andDo(print());
    }
}
