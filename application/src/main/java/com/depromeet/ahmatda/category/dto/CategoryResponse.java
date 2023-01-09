package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.recommend.RecommendCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private CategoryType type;
    private AhmatdaEmoji emoji;

    public static CategoryResponse createByEntity(final Category category) {
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .type(category.getType())
            .emoji(category.getEmoji())
            .build();
    }

    public static CategoryResponse createByRecommendCategory(final RecommendCategory recommendCategory) {
        return CategoryResponse.builder()
                .id(recommendCategory.getId())
                .name(recommendCategory.getName())
                .type(recommendCategory.getType())
                .emoji(recommendCategory.getEmoji())
                .build();
    }
}
