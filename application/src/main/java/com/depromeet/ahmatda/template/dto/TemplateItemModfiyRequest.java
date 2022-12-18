package com.depromeet.ahmatda.template.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateItemModfiyRequest {
    @NotNull(message = "템플릿을 확인해주세요.")
    private Long templateId;

    @NotNull(message = "수정할 소지품을 확인해주세요.")
    private Long itemId;

    private String modifiedItemName;

    private Boolean isTake;

    private boolean isImportant;
}
