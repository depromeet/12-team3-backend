package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateItemModfiyRequest {
    private Long templateId;

    private Long itemId;

    private String modifiedItemName;

    private boolean isImportant;
}
