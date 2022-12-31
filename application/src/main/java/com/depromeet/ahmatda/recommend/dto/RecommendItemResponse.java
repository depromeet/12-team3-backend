package com.depromeet.ahmatda.recommend.dto;

import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecommendItemResponse {
    private List<String> items;

    public static RecommendItemResponse from(List<RecommendItem> recommendItems) {
        return RecommendItemResponse.builder()
                .items(recommendItems.stream()
                        .map(recommendItem -> recommendItem.getItemName())
                        .collect(Collectors.toList()))
                .build();
    }
}
