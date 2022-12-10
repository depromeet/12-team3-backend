package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String type;
    private AhmatdaEmoji emoji;

    public static CategoryResponse createByEntity(final Category category) {
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .type(category.getType())
            .emoji(category.getEmoji())
            .build();
    }
}
