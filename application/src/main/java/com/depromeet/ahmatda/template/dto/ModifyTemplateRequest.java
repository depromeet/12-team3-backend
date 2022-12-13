package com.depromeet.ahmatda.template.dto;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTemplateRequest {
    private Long templateId;

    private String templateName;

    private Long categoryId;

    private boolean pin;
}
