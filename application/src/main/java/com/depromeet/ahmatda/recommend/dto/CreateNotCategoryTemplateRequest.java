package com.depromeet.ahmatda.recommend.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotCategoryTemplateRequest {
    private String templateName;
    private List<String> items;
}
