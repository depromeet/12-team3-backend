package com.depromeet.ahmatda.recommend.service;

import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.RecommendItemResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;

import java.util.List;

public interface RecommendService {

    List<RecommendTemplateResponse> findByCategoryId(Long categoryId);

    void addUserTemplate(String userToken, RecommendAddUserTemplateRequest recommendAddUserTemplateRequest);

    RecommendItemResponse findByRecommendItems(Long categoryId);
}
