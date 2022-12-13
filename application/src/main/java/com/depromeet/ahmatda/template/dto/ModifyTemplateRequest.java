package com.depromeet.ahmatda.template.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModifyTemplateRequest {
    private Long templateId;

    private String templateName;

    private Long categoryId;

    private boolean pin;
}
