package com.depromeet.ahmatda.recommend.service.impl;


import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.adaptor.RecommendAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.RecommendItemResponse;
import com.depromeet.ahmatda.recommend.dto.RecommendTemplateResponse;
import com.depromeet.ahmatda.recommend.exception.RecommendException;
import com.depromeet.ahmatda.recommend.service.RecommendService;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendTemplateService implements RecommendService {

    private final RecommendAdaptor recommendAdaptor;
    private final CategoryService categoryService;
    private final TemplateService templateService;

    private final UserService userService;

    @Override
    public List<RecommendTemplateResponse> findByCategoryId(Long categoryId) {
        List<RecommendTemplate> recommendTemplates = recommendAdaptor.findByCategoryId(categoryId);
        return recommendTemplates.stream()
                .map(RecommendTemplate -> RecommendTemplateResponse.createByEntity(RecommendTemplate))
                .collect(Collectors.toList());
    }

    @Override
    public void addUserTemplate(String userToken, RecommendAddUserTemplateRequest recommendAddUserTemplateRequest) {

        User user = userService.getUserByToken(userToken)
                .orElseThrow(() -> new RecommendException(ErrorCode.USER_NOT_FOUND));

        if(recommendAddUserTemplateRequest.getCreateAllRequest() != null) {
            Category category =  categoryService.createCategory(user, recommendAddUserTemplateRequest.getCreateAllRequest().getCategoryRequest());

            List<TemplateItemRequest> items = recommendAddUserTemplateRequest.getCreateAllRequest().getCreateTemplateRequest().getItems().stream()
                    .map(itemName ->
                            TemplateItemRequest.builder()
                                    .categoryId(category.getId())
                                    .name(itemName)
                                    .build()
                    ).collect(Collectors.toList());

            CreateTemplateRequest createTemplateRequest = CreateTemplateRequest.builder()
                    .templateName(recommendAddUserTemplateRequest.getCreateAllRequest().getCreateTemplateRequest().getTemplateName())
                    .categoryId(category.getId())
                    .items(items)
                    .build();
            templateService.createUserTemplate(user.getId(), createTemplateRequest);
        }

        if(recommendAddUserTemplateRequest.getCreateTemplateRequest() != null) {
            List<TemplateItemRequest> items = recommendAddUserTemplateRequest.getCreateTemplateRequest().getItems().stream()
                    .map(itemName ->
                            TemplateItemRequest.builder()
                                    .categoryId(recommendAddUserTemplateRequest.getCreateTemplateRequest().getUserCategoryId())
                                    .name(itemName)
                                    .build()
                    ).collect(Collectors.toList());

            CreateTemplateRequest createTemplateRequest = CreateTemplateRequest.builder()
                    .templateName(recommendAddUserTemplateRequest.getCreateTemplateRequest().getTemplateName())
                    .items(items)
                    .categoryId(recommendAddUserTemplateRequest.getCreateTemplateRequest().getUserCategoryId())
                    .build();

            templateService.createUserTemplate(user.getId(), createTemplateRequest);
        }

        if(recommendAddUserTemplateRequest.getTemplateAddItemsRequest() != null) {
            templateService.templateAddItems(user.getUserToken(), recommendAddUserTemplateRequest.getTemplateAddItemsRequest());
        }
    }

    @Override
    public RecommendItemResponse findByRecommendItems(Long categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        List<RecommendItem> recommendItems = recommendAdaptor.findByItemsCategoryType(categoryResponse.getType());
        RecommendItemResponse recommendItemResponse = RecommendItemResponse.from(recommendItems);
        if (recommendItemResponse.getItems() != null) {
            recommendItemResponse.setItems(recommendItemsRandomShuffle(recommendItemResponse.getItems()));
        }
        return recommendItemResponse;
    }

    private List<String> recommendItemsRandomShuffle(List<String> items) {
        Collections.shuffle(items);
        if (items.size() > 5) {
            return items.subList(0, 5);
        }
        return items;
    }


}
