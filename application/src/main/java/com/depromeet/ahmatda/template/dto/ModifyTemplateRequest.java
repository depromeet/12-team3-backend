package com.depromeet.ahmatda.template.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTemplateRequest {
    @NotNull(message = "템플릿을 확인해주세요.")
    private Long templateId;

    private String templateName;

    @NotNull(message = "카테고리를 확인해주세요.")
    private Long categoryId;

    private boolean pin;
}
