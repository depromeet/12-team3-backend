package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String type;
    private String emoji;

    public static CategoryResponse createByEntity(Category category) {
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .type(category.getType())
            .emoji(category.getEmoji())
            .build();
    }
}
