package com.depromeet.ahmatda.recommend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SectionRecommendItemResponse {
    private String comment;
    private List<String> items;
}
