package com.depromeet.ahmatda.onboard;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.onboard.OnBoardingCategory;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.token.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final CategoryService categoryService;
    private final TemplateService templateService;

    @Transactional(propagation = Propagation.MANDATORY)
    public UserToken setUserOnboarding(final User user, OnboardingRequest request) {
        Category category = createOnboardingCategory(user, request.getCategory());
        createOnboardingTemplate(user, request.getTemplateName(), category, request.getItems());

        return new UserToken(user.getUserToken());
    }

    private Category createOnboardingCategory(User user, OnBoardingCategory category) {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name(category.getCategoryName())
                .type(CategoryType.valueOf(category.name()))
                .emoji(category.getEmoji())
                .build();

        return categoryService.createCategory(user, categoryRequest);
    }

    private void createOnboardingTemplate(User user, String templateName, Category category, List<String> items) {
        long categoryId = category.getId();

        List<TemplateItemRequest> itemsRequests = items.stream()
                .map(itemName ->
                        TemplateItemRequest.builder()
                                .categoryId(categoryId)
                                .name(itemName)
                                .build()
                ).collect(Collectors.toList());

        CreateTemplateRequest templateRequest = CreateTemplateRequest.builder()
                .templateName(templateName)
                .categoryId(category.getId())
                .items(itemsRequests)
                .build();

        templateService.createUserTemplate(user, templateRequest);
    }
}
