package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateItemRequest {
    private final Long categoryId;

    private final String name;
}
