package com.depromeet.ahmatda.recommend.dto;

import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RecommendTemplateItemResponse {
    private final Long id;

    private final Long categoryId;

    private final Long recommendTemplateId;

    private final String name;

    public static RecommendTemplateItemResponse from(RecommendItem recommendItem) {
        return RecommendTemplateItemResponse.builder()
                .id(recommendItem.getId())
                .categoryId(recommendItem.getCategory().getId())
                .recommendTemplateId(recommendItem.getRecommendTemplate().getId())
                .name(recommendItem.getItemName())
                .build();
    }
}
