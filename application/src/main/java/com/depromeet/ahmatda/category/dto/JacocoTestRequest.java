package com.depromeet.ahmatda.category.dto;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JacocoTestRequest {

    @NotBlank(message = "카테고리 이름은 공백 또는 NULL 일 수 없습니다.")
    @Size(max = 10, message = "카테고리 이름의 길이는 열 글자 이하여야 합니다.")
    private String name;

    @NotNull(message = "일치하는 카테고리 타입이 없습니다.")
    private CategoryType type;

    @NotNull(message = "일치하는 이모지가 없습니다.")
    private AhmatdaEmoji emoji;

    public static Category toEntity(User user, JacocoTestRequest categoryRequest) {
        return Category.builder()
                .user(user)
                .type(categoryRequest.type)
                .name(categoryRequest.name)
                .emoji(categoryRequest.emoji)
                .build();
    }

    public Category modifyEntity(Category category) {
        return Category.builder()
                .id(category.getId())
                .user(category.getUser())
                .name(this.name)
                .type(this.type)
                .emoji(this.emoji)
                .build();
    }
}
