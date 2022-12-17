package com.depromeet.ahmatda.recommend.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCreateTemplateRequest {
    private String templateName;

    private Long userCategoryId;

    private List<String> items;
}
