package com.depromeet.ahmatda.recommend;


import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.SectionRecommendItemResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;
import com.depromeet.ahmatda.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping(value ="/category")
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getRecommendCategories() {
        final List<CategoryResponse> categoryResponses = recommendService.getRecommendCategory();
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @GetMapping(value = "/templates")
    public ResponseEntity<RestResponse<List<RecommendTemplateResponse>>> getRecommendTemplates(@RequestParam("category") Long recommendCategoryId) {
        //TODO : 유저의 정보로 추천템플릿을 보여주는 로직필요
        List<RecommendTemplateResponse> recommendTemplateResponses = recommendService.findByRecommendCategoryId(recommendCategoryId);
        return ResponseEntity.ok().body(RestResponse.ok(recommendTemplateResponses));
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> userTemplateAddRecommendItem(HttpServletRequest request, @RequestBody RecommendAddUserTemplateRequest recommendAddUserTemplateRequest) {
        String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        recommendService.addUserTemplate(userToken, recommendAddUserTemplateRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @GetMapping(value = "/items")
    public ResponseEntity<RestResponse<SectionRecommendItemResponse>> getRecommendItems(String categoryType) {
        SectionRecommendItemResponse sectionRecommendItemResponses = recommendService.getRandomSectionItems(categoryType);
        return ResponseEntity.ok().body(RestResponse.ok(sectionRecommendItemResponses));
    }
}
