package com.depromeet.ahmatda.user.service.impl;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.onboard.OnBoardingCategory;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpOnBoardRequest;
import com.depromeet.ahmatda.user.service.UserService;
import com.depromeet.ahmatda.user.token.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceUserService implements UserService {

    private final UserAdaptor userAdaptor;
    private final CategoryService categoryService;
    private final TemplateService templateService;

    @Override
    public UserRegisterCode checkUser(UserToken userToken) {
        String userTokenValue = userToken.getValue();

        return UserRegisterCode.getByUserFound(
                userAdaptor.findByUserToken(userTokenValue).isPresent()
        );
    }

    @Override
    @Transactional
    public UserToken createUser(SignUpOnBoardRequest request) {
        UserToken userToken = UserToken.generate();

        User user = userAdaptor.createUser(
            User.createUserWithUserToken(userToken.getValue())
        );

        long userId = user.getId();
        Category category = createOnboardingCategory(userId, request.getCategory());
        createOnboardingTemplate(userId, request.getTemplateName(), category, request.getItems());

        return new UserToken(user.getUserToken());
    }

    private Category createOnboardingCategory(Long userId, OnBoardingCategory category) {
        CategoryRequest categoryRequest = CategoryRequest.builder()
            .name(category.getCategoryName())
            .type(category.getCategoryName())
            .emoji(category.getEmoji())
            .build();

        return categoryService.createCategory(userId.toString(), categoryRequest);
    }

    private void createOnboardingTemplate(Long userId, String templateName, Category category, List<String> items) {
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

        templateService.createUserTemplate(userId.toString(), templateRequest);
    }
}
