package com.depromeet.ahmatda.template.dto;

import com.depromeet.ahmatda.domain.item.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateItemResponse {
    private final Long id;

    private final Long templateId;

    private final Long categoryId;

    private final String name;

    private final Long alarmId;

    private final boolean isTake;

    private final boolean isImportant;

    public static TemplateItemResponse from(Item item) {
        return TemplateItemResponse.builder()
                .id(item.getId())
                .templateId(item.getTemplate().getId())
                .categoryId(item.getCategoryId())
                .name(item.getName())
                .alarmId(item.getAlarmId())
                .isTake(item.isTake())
                .isImportant(item.isImportant())
                .build();
    }
}
