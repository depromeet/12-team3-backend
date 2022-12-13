package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateAddItemRequest {
    private Long templateId;

    private Long categoryId;

    private String itemName;

    private boolean important;
}
