package com.depromeet.ahmatda.template.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateItemRequest {
    @NotNull(message = "카테고리를 확인해주세요.")
    private Long categoryId;

    @NotNull(message = "소지품명은 필수입니다.")
    private String name;
}
