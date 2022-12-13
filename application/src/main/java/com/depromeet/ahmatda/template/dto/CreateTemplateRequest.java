package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateTemplateRequest {
    private String templateName;

    private Long categoryId;

    private final List<TemplateItemRequest> items;
}
