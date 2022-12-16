package com.depromeet.ahmatda.recommend.service;

import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;

import java.util.List;

public interface RecommendService {

    List<RecommendTemplateResponse> findByCategory_Id(Long categoryId);
}
