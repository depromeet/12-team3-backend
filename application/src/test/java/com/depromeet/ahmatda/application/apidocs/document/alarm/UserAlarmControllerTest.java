package com.depromeet.ahmatda.application.apidocs.document.alarm;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.UserAlarmRequest;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.application.apidocs.util.DocumentEnumLinkGenerator;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.alarm.AlarmResponse;
import com.depromeet.ahmatda.domain.alarm.TimeOption;
import com.depromeet.ahmatda.domain.alarm.AlarmType;
import com.depromeet.ahmatda.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.depromeet.ahmatda.application.apidocs.document.alarm.AlarmTestConst.*;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentEnumLinkGenerator.generateLinkCode;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentFormatGenerator.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserAlarmControllerTest extends ApiDocumentationTest {

    @DisplayName("템플릿 ID 로 알람 조회")
    @Test
    void checkUserTest() throws Exception {
        // given
        User user = User.builder().id(USER_ID).build();

        given(alarmService.getAlarm(USER_ID, TEMPLATE_ID)).willReturn(Optional.of(dailyAlarm));
        given(userService.getUserByToken(USER_TOKEN)).willReturn(Optional.of(user));

        String response = objectMapper.writeValueAsString(RestResponse.ok(AlarmResponse.of(dailyAlarm)));

        // when
        ResultActions result = this.mockMvc.perform(
            get("/api/alarm")
                .header(HttpHeader.USER_TOKEN, USER_TOKEN)
                .param("templateId", String.valueOf(TEMPLATE_ID))
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("get-alarm-by-template-id",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName("ahmatda-user-token").description("유저 UUID")
                ),
                requestParameters(
                    parameterWithName("templateId").description("템플릿 ID")
                ),
                responseFields(beneathPath("result").withSubsectionId("result"),
                    fieldWithPath("templateId").type(NUMBER).description("템플릿 id"),
                    fieldWithPath("alarmType").type(STRING).description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.ALARM_TYPE)),
                    fieldWithPath("dayOfWeek").type(NULL).description("요일별 알람 - 알람 요일(미구현)"),
                    fieldWithPath("alarmTime").type(NULL).description("요일별 알람 - 알람 시간(미구현)"),
                    fieldWithPath("replayType").type(NULL).description("요일별 알람 - 반복 유형(미구현)"),
                    fieldWithPath("alarmDateTime").type(STRING).attributes(getDateTimeFormat()).description("날짜별 알람 - 알람 날짜 및 시간"),
                    fieldWithPath("timeOption").type(STRING).description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.ALARM_TIME_OPTION)),
                    fieldWithPath("activated").type(BOOLEAN).description("알람 활성화 여부")
                )
            ));
    }

    @DisplayName("날짜별 알람 추가")
    @Test
    void dailyAlarmCreateTest() throws Exception {
        // given
        User user = User.builder().id(USER_ID).build();

        UserAlarmRequest request = new UserAlarmRequest(
            AlarmType.DAILY,
            1L,
            true,
            LocalDateTime.of(2020, 1, 1, 1, 1, 1),
            TimeOption.TEN_MINUTES
        );

        given(alarmService.setTemplateDailyAlarm(user, request)).willReturn(dailyAlarm);
        given(userService.getUserByToken(USER_TOKEN)).willReturn(Optional.of(user));

        String response = objectMapper.writeValueAsString(RestResponse.ok());

        // when
        ResultActions result = this.mockMvc.perform(
            post("/api/alarm/daily")
                .header(HttpHeader.USER_TOKEN, USER_TOKEN)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("create-alarm-daily",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName("ahmatda-user-token").description("유저 UUID")
                ),
                requestFields(
                    fieldWithPath("alarmType").type(STRING).attributes(getConstraintsAttribute(UserAlarmRequest.class, "alarmType")).description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.ALARM_TYPE)),
                    fieldWithPath("templateId").type(NUMBER).attributes(getConstraintsAttribute(UserAlarmRequest.class, "templateId")).description("템플릿 id"),
                    fieldWithPath("isActivated").type(BOOLEAN).attributes(getConstraintsAttribute(UserAlarmRequest.class, "isActivated")).description("활성화 여부"),
                    fieldWithPath("alarmDateTime").type(STRING).attributes(getDateTimeFormat(), getConstraintsAttribute(UserAlarmRequest.class, "alarmDateTime")).description("알람 예약 날짜 및 시간"),
                    fieldWithPath("alarmTimeOption").type(STRING).attributes(getConstraintsAttribute(UserAlarmRequest.class, "alarmTimeOption")).description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.ALARM_TIME_OPTION))
                )
            ));
    }
}