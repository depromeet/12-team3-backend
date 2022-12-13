package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateItemModfiyRequest {
    private Long templateId;

    private Long itemId;

    private String modifiedItemName;

    private Boolean isTake;

    private boolean isImportant;
}
