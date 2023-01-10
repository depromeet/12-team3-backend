package com.depromeet.ahmatda.template.dto;
import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
public class TemplateResponse {
    private Long id;

    private String userToken;

    private String templateName;

    private Long categoryId;

    private String alarmInfo;

    private final List<TemplateItemResponse> items;

    private boolean pin;

    public static TemplateResponse createByEntity(Template template, String alarmInfo) {
        alarmInfo = alarmInfo == null ? "" : alarmInfo;

        List<TemplateItemResponse> items = template.getItems().stream()
                .sorted(Comparator.comparing(Item::isTake).reversed()
                        .thenComparing(Item::isImportant).reversed()
                        .thenComparing(BaseTimeEntity::getCreatedAt))
                .map(TemplateItemResponse::from)
                .collect(Collectors.toList());

        return TemplateResponse.builder()
                .id(template.getId())
                .userToken(template.getUser().getUserToken())
                .templateName(template.getTemplateName())
                .alarmInfo(alarmInfo)
                .categoryId(template.getCategory().getId())
                .items(items)
                .pin(template.isPin())
                .build();
    }
}
