//package com.depromeet.ahmatda.application.apidocs.document.user;
//
//import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
//import com.depromeet.ahmatda.common.response.ErrorCode;
//import com.depromeet.ahmatda.common.response.RestResponse;
//import com.depromeet.ahmatda.user.dto.SignUpRequestDto;
//import com.depromeet.ahmatda.user.exception.UserExistException;
//import org.junit.jupiter.api.*;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
//import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;
//import static com.depromeet.ahmatda.application.apidocs.util.DocumentConstraintsGenerator.getConstraintsAttribute;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class UserControllerTest extends ApiDocumentationTest {
//
//    @Test
//    void createUser() throws Exception {
//        // given
//        SignUpRequestDto requestDto = new SignUpRequestDto("abc");
//        String request = objectMapper.writeValueAsString(requestDto);
//        String response = objectMapper.writeValueAsString(RestResponse.ok());
//
//        // when
//        ResultActions result = this.mockMvc.perform(
//            post("/api/user")
//                .content(request)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//        );
//
//        // then
//        result.andExpect(status().isOk())
//            .andExpect(content().string(response))
//            .andDo(print())
//            .andDo(MockMvcRestDocumentation.document("user-sign-up",
//                getDocumentRequest(),
//                getDocumentResponse(),
//                requestFields(
//                    fieldWithPath("deviceId").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceId")).description("기기번호")
//                )
//            ));
//    }
//
//    @Test
//    @DisplayName("이미 유저가 존재할 때")
//    void existUser() throws Exception {
//        // given
//        SignUpRequestDto requestDto = new SignUpRequestDto("abc");
//        String request = objectMapper.writeValueAsString(requestDto);
//        String response = objectMapper.writeValueAsString(RestResponse.error(ErrorCode.EXIST_USER));
//        doThrow(new UserExistException(ErrorCode.EXIST_USER)).when(userService).createUser(requestDto);
//
//        // when
//        ResultActions result = this.mockMvc.perform(
//                post("/api/user")
//                        .content(request)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        // then
//        result.andExpect(status().isBadRequest())
//                .andExpect(content().string(response))
//                .andDo(print())
//                .andDo(MockMvcRestDocumentation.document("user-sign-up-exist-error",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("deviceId").type(JsonFieldType.STRING).attributes(getConstraintsAttribute(SignUpRequestDto.class, "deviceId")).description("기기번호")
//                        )
//                ));
//    }
//}
