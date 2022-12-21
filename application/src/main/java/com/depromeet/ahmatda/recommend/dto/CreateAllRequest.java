package com.depromeet.ahmatda.recommend.dto;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import lombok.*;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateAllRequest {

    // 새로만들 카테고리 정보
    private CategoryRequest categoryRequest;

    // 새로만들 템플릿 정보
    private CreateNotCategoryTemplateRequest createTemplateRequest;

}
