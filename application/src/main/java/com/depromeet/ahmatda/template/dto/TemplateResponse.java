package com.depromeet.ahmatda.template.dto;
import com.depromeet.ahmatda.domain.Item;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class TemplateResponse {
    private Long id;

    private String userToken;

    private String templateName;

    private Long categoryId;

    private final List<TemplateItemResponse> items;

    public static TemplateResponse createByEntity(Template template) {
        List<TemplateItemResponse> items = new ArrayList<>();
        for (Item item: template.getItems()) {
            items.add(TemplateItemResponse.from(item));
        }
        return TemplateResponse.builder()
                .id(template.getId())
                .userToken(template.getUser().getUserToken())
                .templateName(template.getTemplateName())
                .categoryId(template.getCategory().getId())
                .items(items)
                .build();
    }
}
