package com.depromeet.ahmatda.recommend.service.impl;


import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.adaptor.RecommendAdaptor;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;
import com.depromeet.ahmatda.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendTemplateService implements RecommendService {

    private final RecommendAdaptor recommendAdaptor;

    @Override
    public List<RecommendTemplateResponse> findByCategory_Id(Long categoryId) {
        List<RecommendTemplate> recommendTemplates = recommendAdaptor.findByCategory_Id(categoryId);
        return recommendTemplates.stream()
                .map(RecommendTemplate -> RecommendTemplateResponse.createByEntity(RecommendTemplate))
                .collect(Collectors.toList());
    }
}
