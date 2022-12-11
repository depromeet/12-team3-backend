package com.depromeet.ahmatda.template.dto;
import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class TemplateResponse {
    private Long id;

    private String userToken;

    private String templateName;

    private Long categoryId;

    private final List<TemplateItemResponse> items;

    private boolean pin;

    public static TemplateResponse createByEntity(Template template) {
        List<TemplateItemResponse> items = template.getItems().stream()
                .map(TemplateItemResponse::from)
                .collect(Collectors.toList());

        return TemplateResponse.builder()
                .id(template.getId())
                .userToken(template.getUser().getUserToken())
                .templateName(template.getTemplateName())
                .categoryId(template.getCategory().getId())
                .items(items)
                .pin(template.isPin())
                .build();
    }
}
