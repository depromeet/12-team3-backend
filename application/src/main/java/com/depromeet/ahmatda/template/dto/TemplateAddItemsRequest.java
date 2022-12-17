package com.depromeet.ahmatda.template.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TemplateAddItemsRequest {

    //기존카테고리 정보
    private Long userCategoryId;

    //기존템플릿 정보
    private Long userTemplateId;

    //추가할 아이템 List
    private List<String> items;
}
