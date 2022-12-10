package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateItemRequest {
    private final Long categoryId;

    private final String name;
}
