package com.depromeet.ahmatda.template.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateAddItemRequest {
    private Long templateId;

    private Long categoryId;

    private String itemName;

    private boolean important;
}
