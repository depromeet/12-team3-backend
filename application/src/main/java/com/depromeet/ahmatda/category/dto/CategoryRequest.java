package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.Emoji;
import com.depromeet.ahmatda.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
