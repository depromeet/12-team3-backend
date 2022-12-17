package com.depromeet.ahmatda.recommend.dto;


import com.depromeet.ahmatda.template.dto.TemplateAddItemsRequest;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecommendAddUserTemplateRequest {
    //새로운카테고리 + 새로운템플릿에 추가
    private CreateAllRequest createAllRequest;
    //기존카테고리 + 기존템플릿에 추가
    private TemplateAddItemsRequest templateAddItemsRequest;
    //기존카테고리 + 새로운 템플릿에 추가
    private RecommendCreateTemplateRequest createTemplateRequest;
}
