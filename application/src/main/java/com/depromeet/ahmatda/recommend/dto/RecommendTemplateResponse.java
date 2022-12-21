package com.depromeet.ahmatda.recommend.dto;

import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class RecommendTemplateResponse {
    private Long id;

    private String templateName;

    private Long categoryId;

    private final List<RecommendTemplateItemResponse> items;

    public static RecommendTemplateResponse createByEntity(RecommendTemplate recommendTemplate) {
        List<RecommendTemplateItemResponse> items = recommendTemplate.getRecommendItems().stream()
                .map(RecommendTemplateItemResponse::from)
                .collect(Collectors.toList());

        return RecommendTemplateResponse.builder()
                .id(recommendTemplate.getId())
                .templateName(recommendTemplate.getTemplateName())
                .categoryId(recommendTemplate.getCategory().getId())
                .items(items)
                .build();
    }
}
