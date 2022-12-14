package com.depromeet.ahmatda.template.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateAddItemRequest {
    @NotNull(message = "템플릿을 확인해주세요.")
    private Long templateId;

    @NotNull(message = "카테고리를 확인해주세요.")
    private Long categoryId;

    private String itemName;

    private boolean important;
}
