package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateTemplateRequest {
    private String templateName;

    private Long categoryId;

    private final List<TemplateItemRequest> items;
}
