package com.depromeet.ahmatda.application.apidocs.document.alarm;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.history.dto.AlarmMessageHistoryResponse;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAlarmHistoryControllerTest extends ApiDocumentationTest {

    private final String TEST_USER_TOKEN = UUID.randomUUID().toString();

    @Test
    void alarm() throws Exception {
        AlarmMessageHistoryResponse alarmMessageHistoryResponse = AlarmMessageHistoryResponse.builder()
                .ahmatdaEmoji(AhmatdaEmoji.BOWLING)
                .message("아맞다! 정신 챙기세요.")
                .elapsedSentTime("2시간 전")
                .build();

        List<AlarmMessageHistoryResponse> alarmMessageHistoryResponses = Arrays.asList(alarmMessageHistoryResponse);
        String response = objectMapper.writeValueAsString(RestResponse.ok(alarmMessageHistoryResponses));

        given(alarmMessageHistoryService.getUserAlarmHistories(TEST_USER_TOKEN))
                .willReturn(alarmMessageHistoryResponses);

        ResultActions result = mockMvc.perform(get("/api/alarm/history")
                .header(HttpHeader.USER_TOKEN, TEST_USER_TOKEN));

        result.andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(print())
                .andDo(document("alarm-history",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("유저 토큰")
                        ),
                        responseFields(
                                beneathPath("result").withSubsectionId("result"),
                                fieldWithPath("ahmatdaEmoji").type(STRING).description("이모지 타입"),
                                fieldWithPath("message").type(STRING).description("알림메시지"),
                                fieldWithPath("elapsedSentTime").type(STRING).description("경과 시간")
                        )
                ));
    }
}
