package com.depromeet.ahmatda.template.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDeleteItemRequest {
    private Long templateId;

    private Long itemId;
}
