package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateDeleteItemRequest {
    private Long templateId;

    private Long itemId;
}
