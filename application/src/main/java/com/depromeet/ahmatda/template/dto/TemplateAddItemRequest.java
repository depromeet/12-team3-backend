package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateAddItemRequest {
    private Long templateId;

    private Long categoryId;

    private String itemName;

    private boolean important;
}
