package com.depromeet.ahmatda.recommend.service;

import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.SectionRecommendItemResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;

import java.util.List;

public interface RecommendService {

    List<CategoryResponse> getRecommendCategory();

    List<RecommendTemplateResponse> findByRecommendCategoryId(Long recommendCategoryId);

    void addUserTemplate(String userToken, RecommendAddUserTemplateRequest recommendAddUserTemplateRequest);

    SectionRecommendItemResponse getRandomSectionItems(Long userCategoryId);
}
