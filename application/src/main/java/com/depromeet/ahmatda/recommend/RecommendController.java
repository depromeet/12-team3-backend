package com.depromeet.ahmatda.recommend;


import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.RecommendItemResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;
import com.depromeet.ahmatda.recommend.service.RecommendService;
import com.depromeet.ahmatda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

    private final CategoryService categoryService;

    private final RecommendService recommendService;

    @GetMapping(value ="/category")
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getRecommendCategories() {
        //TODO: 추천카테고리 조회 기준 확립 필요
        final List<CategoryResponse> categoryResponses = categoryService.getRecommendCategory();
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @GetMapping(value = "/templates")
    public ResponseEntity<RestResponse<List<RecommendTemplateResponse>>> getRecommendTemplates(@RequestParam("category") Long categoryId) {
        //TODO : 유저의 정보로 추천템플릿을 보여주는 로직필요
        List<RecommendTemplateResponse> recommendTemplateResponses = recommendService.findByCategoryId(categoryId);
        return ResponseEntity.ok().body(RestResponse.ok(recommendTemplateResponses));
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> userTemplateAddRecommendItem(HttpServletRequest request, @RequestBody RecommendAddUserTemplateRequest recommendAddUserTemplateRequest) {
        String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        recommendService.addUserTemplate(userToken, recommendAddUserTemplateRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @GetMapping(value = "/items")
    public ResponseEntity<RestResponse<RecommendItemResponse>> getRecommendItems(@RequestParam("category") Long categoryId) {
        RecommendItemResponse recommendItemResponses = recommendService.findByRecommendItems(categoryId);
        return ResponseEntity.ok().body(RestResponse.ok(recommendItemResponses));
    }
}
