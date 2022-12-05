package com.depromeet.ahmatda.application.apidocs.document.user;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.user.dto.SignUpRequestDto;
import com.depromeet.ahmatda.user.exception.UserExistException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ApiDocumentationTest {
    private final String iosDeviceId = "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F9";
    private final String androidDeviceId = "dd96dec43fb81c97";

    @Test
    void createUser() throws Exception {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto(DeviceCode.IOS, iosDeviceId);
        String request = objectMapper.writeValueAsString(requestDto);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        // when
        ResultActions result = this.mockMvc.perform(
            post("/api/user")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("user-sign-up",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("deviceCode").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceCode")).description("IOS / ANDROID"),
                    fieldWithPath("deviceId").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceId")).description("기기번호")
                )
            ));
    }

    @Test
    @DisplayName("이미 유저가 존재할 때")
    void existUser() throws Exception {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto(DeviceCode.IOS, iosDeviceId);
        String request = objectMapper.writeValueAsString(requestDto);
        String response = getObjectMapper().writeValueAsString(RestResponse.error(ErrorCode.EXIST_USER));
        doThrow(new UserExistException(ErrorCode.EXIST_USER)).when(userService).createUser(requestDto);

        // when
        ResultActions result = this.mockMvc.perform(
                post("/api/user")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest())
            .andExpect(content().string(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("user-sign-up-exist-error",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("deviceCode").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceCode")).description("IOS / ANDROID"),
                    fieldWithPath("deviceId").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceId")).description("기기번호")
                )
            ));
    }

    @Test
    @DisplayName("IOS deviceId 형식이 아니면 에러")
    void deviceIdDeviceCodeValidate() throws Exception {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto(DeviceCode.IOS, "abc");
        String request = objectMapper.writeValueAsString(requestDto);
        String response = getObjectMapper().writeValueAsString(RestResponse.error(ErrorCode.BINDING_ERROR, Map.of("deviceCode", "deviceCode-deviceId 값이 유효하지 않습니다")));

        // when
        ResultActions result = this.mockMvc.perform(
                post("/api/user")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(response))
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("user-sign-up-validate-error",
                        getDocumentRequest(),
                        getDocumentResponse()
                ));
    }
}
