package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.Emoji;
import com.depromeet.ahmatda.domain.user.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CategoryRequest {

    private String name;
    private String type;
    private Emoji emoji;

    public static Category toEntity(User user, CategoryRequest categoryRequest) {
        return Category.builder()
                .user(user)
                .type(categoryRequest.name)
                .name(categoryRequest.type)
                .emoji(categoryRequest.emoji)
                .build();
    }

    public Category modifyEntity(Category category) {
        return Category.builder()
                .id(category.getId())
                .name(this.name)
                .type(this.type)
                .emoji(this.emoji)
                .build();
    }
}
