package com.depromeet.ahmatda.template.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CreateTemplateRequest {
    private String templateName;

    private Long categoryId;

    private final List<TemplateItemRequest> items;
}
