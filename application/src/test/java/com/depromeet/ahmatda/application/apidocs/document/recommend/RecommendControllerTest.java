package com.depromeet.ahmatda.application.apidocs.document.recommend;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.application.apidocs.document.ApiDocumentationTest;
import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.recommend.dto.*;
import com.depromeet.ahmatda.template.dto.TemplateAddItemsRequest;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.stream.Collectors;

import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentRequest;
import static com.depromeet.ahmatda.application.apidocs.util.ApiDocsUtil.getDocumentResponse;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecommendControllerTest extends ApiDocumentationTest {

    @Test
    void ???????????????_??????() throws Exception {
        Long userCategoryId = 1L;
        List<String> items = List.of("??????", "??????", "?????????", "?????????");
        SectionRecommendItemResponse sectionRecommendItemResponse = SectionRecommendItemResponse.builder()
                .comment("?????????! ????????? ?????? ????????????!")
                .items(items)
                .build();

        given(recommendService.getRandomSectionItems(userCategoryId)).willReturn(sectionRecommendItemResponse);

        mockMvc.perform(get("/api/recommend/items?category={categoryId}", userCategoryId))
                .andExpect(status().isOk())
                .andDo(document("recommend-items",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("category").description("?????? ???????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("result.comment").type(JsonFieldType.STRING).description("??????????????? ????????? ??????"),
                                fieldWithPath("result.items").type(JsonFieldType.ARRAY).description("?????????????????????"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("??????"))))
                .andDo(print());
    }
    @Test
    void ??????????????????_??????() throws Exception {
        List<CategoryResponse> categoryResponses = List.of(
                CategoryResponse.builder()
                        .id(1L).emoji(AhmatdaEmoji.BOWLING)
                        .type(CategoryType.DAILY).name("DAILY").build(),
                CategoryResponse.builder()
                        .id(2L).emoji(AhmatdaEmoji.EMPTY_CARD)
                        .type(CategoryType.EXERCISE).name("HEALTH").build());

        given(recommendService.getRecommendCategory()).willReturn(categoryResponses);

        mockMvc.perform(get("/api/recommend/category"))
                .andExpect(status().isOk())
                .andDo(document("recommend-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.ARRAY).description("??????"),
                                fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("???????????? ID"),
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("result[].type").type(JsonFieldType.STRING)
                                        .description("???????????? ??????"),
                                fieldWithPath("result[].emoji").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("??????"))))
                .andDo(print());
    }
    @Test
    void ???????????????_??????() throws Exception {
        User userWithDeviceId = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userId = userWithDeviceId.getUserToken();
        Long categoryId = 1L;

        List<RecommendTemplateItemResponse> items = List.of(
                RecommendTemplateItemResponse.builder()
                        .id(1L)
                        .recommendTemplateId(100L)
                        .name("?????????")
                        .build(),
                RecommendTemplateItemResponse.builder()
                        .id(2L)
                        .recommendTemplateId(100L)
                        .name("?????????")
                        .build());
        List<RecommendTemplateResponse> recommendTemplateResponses = List.of(
                RecommendTemplateResponse.builder()
                        .id(100L)
                        .categoryId(categoryId)
                        .templateName("???????????? ????????????").items(items)
                        .build());

        given(recommendService.findByRecommendCategoryId(categoryId)).willReturn(recommendTemplateResponses);

        mockMvc.perform(get("/api/recommend/templates?category={categoryId}", categoryId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("recommend-templates",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("category").description("?????? ???????????? ID")
                        )
                ));
    }

    @Test
    void ???????????????_?????????_??????????????????_??????() throws Exception {
        User user = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userToken = user.getUserToken();
        Long userCategoryId = 1L;
        Long userTemplateId = 10L;
        List<String> items = Lists.newArrayList("??????", "????????????", "?????????");

        // ????????? ???????????? + ????????? ???????????? ??????
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .type(CategoryType.EXERCISE).emoji(AhmatdaEmoji.TUBE).name("CUSTOM")
                .build();

        CreateNotCategoryTemplateRequest createNotCategoryTemplateRequest = CreateNotCategoryTemplateRequest.builder()
                .templateName("?????????????????????")
                .items(items)
                .build();

        CreateAllRequest createAllRequest = CreateAllRequest.builder()
                .categoryRequest(categoryRequest)
                .createTemplateRequest(createNotCategoryTemplateRequest)
                .build();

        RecommendAddUserTemplateRequest recommendAddUserTemplateRequest = RecommendAddUserTemplateRequest.builder()
                .createAllRequest(createAllRequest)
                .build();

        String request = objectMapper.writeValueAsString(recommendAddUserTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/recommend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_TOKEN, userToken))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("recommend-create-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        )))
                .andDo(print());
    }

    @Test
    void ???????????????_??????_???????????????_???????????????() throws Exception {
        User user = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userToken = user.getUserToken();
        Long userCategoryId = 1L;
        Long userTemplateId = 10L;
        List<String> items = Lists.newArrayList("??????", "????????????", "?????????");

        // ?????????????????? + ?????????????????? ??????
        TemplateAddItemsRequest templateAddItemsRequest = TemplateAddItemsRequest.builder()
                .userTemplateId(userTemplateId)
                .userCategoryId(userCategoryId)
                .items(items)
                .build();

        RecommendAddUserTemplateRequest recommendAddUserTemplateRequest = RecommendAddUserTemplateRequest.builder()
                .templateAddItemsRequest(templateAddItemsRequest)
                .build();

        String request = objectMapper.writeValueAsString(recommendAddUserTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/recommend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_TOKEN, userToken))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("recommend-add-user-template-add-items",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        )))
                .andDo(print());
    }
    @Test
    void ???????????????_??????_???????????????_?????????_?????????_??????() throws Exception {
        User user = User.createUserWithUserToken("FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F0");
        String userToken = user.getUserToken();
        Long userCategoryId = 1L;
        Long userTemplateId = 10L;
        List<String> items = Lists.newArrayList("??????", "????????????", "?????????");

        // ?????????????????? + ????????? ???????????? ??????
        RecommendCreateTemplateRequest recommendCreateTemplateRequest = RecommendCreateTemplateRequest.builder()
                .templateName("????????? ???????????????")
                .userCategoryId(userCategoryId)
                .items(items)
                .build();

        RecommendAddUserTemplateRequest recommendAddUserTemplateRequest = RecommendAddUserTemplateRequest.builder()
                .createTemplateRequest(recommendCreateTemplateRequest)
                .build();

        String request = objectMapper.writeValueAsString(recommendAddUserTemplateRequest);
        String response = objectMapper.writeValueAsString(RestResponse.ok());

        mockMvc.perform(post("/api/recommend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header(HttpHeader.USER_TOKEN, userToken))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("recommend-create-template",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("ahmatda-user-token").description("?????? UUID")
                        )))
                .andDo(print());
    }

}
