package com.depromeet.ahmatda.recommend.service.impl;


import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.recommend.RecommendCategory;
import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import com.depromeet.ahmatda.domain.recommend.RecommendSection;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.adaptor.RecommendAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.recommend.dto.SectionRecommendItemData;
import com.depromeet.ahmatda.recommend.dto.RecommendAddUserTemplateRequest;
import com.depromeet.ahmatda.recommend.dto.SectionRecommendItemResponse;
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
    public List<CategoryResponse> getRecommendCategory() {
        List<RecommendCategory> recommendCategories = recommendAdaptor.getAllRecommendCategory();
        return recommendCategories.stream().map(CategoryResponse::createByRecommendCategory).collect(Collectors.toList());
    }

    @Override
    public List<RecommendTemplateResponse> findByRecommendCategoryId(Long recommendCategoryId) {
        List<RecommendTemplate> recommendTemplates = recommendAdaptor.getRtsByRcId(recommendCategoryId);
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
    public SectionRecommendItemResponse getRandomSectionItems(String categoryType) {
        RecommendSection recommendSection = recommendAdaptor.getRandomRecommendSection();

        List<RecommendItem> recommendItems = recommendAdaptor.getRiByRs(recommendSection.getId());
        Collections.shuffle(recommendItems);
        recommendItems = recommendItems.subList(0, 5);

        return new SectionRecommendItemResponse(
          recommendSection.getSectionName(),
          recommendItems.stream()
              .map(recommendItem -> new SectionRecommendItemData(recommendItem.getId(), recommendItem.getItemName()))
              .collect(Collectors.toList())
        );
    }
}
