package com.depromeet.ahmatda.application.apidocs.document.user;

import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.application.apidocs.util.DocumentEnumLinkGenerator;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.onboard.OnBoardingCategory;
import com.depromeet.ahmatda.onboard.OnboardingRequest;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.user.token.UserToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
import static com.depromeet.ahmatda.application.apidocs.util.DocumentEnumLinkGenerator.generateLinkCode;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ApiDocumentationTest {

    @DisplayName("유저_존재_테스트")
    @Test
    void checkUserTest() throws Exception {
        // given
        UserRegisterCode userRegisterResult = UserRegisterCode.REGISTERED;
        UserToken userToken = UserToken.generate();
        given(userService.checkUser(userToken)).willReturn(userRegisterResult);

        String response = objectMapper.writeValueAsString(RestResponse.ok(userRegisterResult));

        // when
        ResultActions result = this.mockMvc.perform(
            get("/api/user")
                .param("userToken", userToken.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(content().string(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("check-user-token",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                    parameterWithName("userToken").description("유저 인증 토큰")
                ),
                responseFields(
                    fieldWithPath("result").description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.USER_REGISTER_CODE)),
                    fieldWithPath("error").description("")
                )
            ));
    }

    @DisplayName("온보딩_및_토큰_발급")
    @Test
    void signInTest() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest(
            new OnboardingRequest(OnBoardingCategory.DAILY, "Tomorrow Checklist", List.of("MacBook", "Airpods"))
        );

        UserToken userToken = UserToken.generate();

        given(userService.createUser(request)).willReturn(userToken);

        String requestStr = objectMapper.writeValueAsString(request);
        String response = objectMapper.writeValueAsString(RestResponse.ok(userToken));

        // when
        ResultActions result = this.mockMvc.perform(
            post("/api/user")
                .content(requestStr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(content().json(response))
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("sign-up-onboarding",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("onboardingRequest").type(JsonFieldType.OBJECT).description("온보딩 요청 객체"),
                    fieldWithPath("onboardingRequest.category").type(JsonFieldType.STRING)
                            .attributes(getConstraintsAttribute(OnboardingRequest.class, "category"))
                            .description(generateLinkCode(DocumentEnumLinkGenerator.DocUrl.ONBOARDING_CATEGORY)),
                    fieldWithPath("onboardingRequest.templateName").type(JsonFieldType.STRING)
                            .attributes(getConstraintsAttribute(OnboardingRequest.class, "templateName"))
                            .description("온보딩 선택 템플릿 이름"),
                    fieldWithPath("onboardingRequest.items").type(JsonFieldType.ARRAY).description("온보딩 선택 아이템 이름 리스트")
                )
            ));
    }
}
