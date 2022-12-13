package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDeleteItemRequest {
    private Long templateId;

    private Long itemId;
}
