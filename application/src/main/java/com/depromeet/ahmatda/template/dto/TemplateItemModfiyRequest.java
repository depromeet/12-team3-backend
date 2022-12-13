package com.depromeet.ahmatda.template.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateItemModfiyRequest {
    private Long templateId;

    private Long itemId;

    private String modifiedItemName;

    private Boolean isTake;

    private boolean isImportant;
}
