package com.depromeet.ahmatda.recommend;


import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
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
        //추천에 띄워줄 유저정보(임시) 추후 고민
        final String userToken = "recommendUser";
        final List<CategoryResponse> categoryResponses = categoryService.getCategoriesByUser(userToken);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @GetMapping(value = "/templates")
    public ResponseEntity<RestResponse<List<RecommendTemplateResponse>>> getRecommendTemplates(HttpServletRequest request, @RequestParam("category") Long categoryId) {
        // 유저검증 필요
        List<RecommendTemplateResponse> recommendTemplateResponses = recommendService.findByCategory_Id(categoryId);
        return ResponseEntity.ok().body(RestResponse.ok(recommendTemplateResponses));
    }
    // 유저템플릿에 추가(기존 템플릿에 추가, 새로운 템플릿으로 추가)

    @PostMapping()
    public ResponseEntity<RestResponse<Object>> UserTemplateAddRecommendItem(HttpServletRequest request, @RequestBody RecommendAddUserTemplateRequest recommendAddUserTemplateRequest) {
        String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        recommendService.addUserTemplate(userToken, recommendAddUserTemplateRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }
}
