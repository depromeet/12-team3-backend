package com.depromeet.ahmatda.category.service.impl;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.category.exception.CategoryUserAuthenticationException;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceCategoryService implements CategoryService {

    private final CategoryAdaptor categoryAdaptor;
    private final UserAdaptor userAdaptor;

    @Override
    public CategoryResponse getCategoryById(final Long id) {
        final Category category = categoryAdaptor.getCategoryById(id)
            .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.createByEntity(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryAdaptor.getCategories();
        return categories.stream()
            .map(category -> CategoryResponse.createByEntity(category))
            .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getCategoriesByUser(final String userToken) {
        final List<Category> userCategories = categoryAdaptor.getCategoriesByUserToken(userToken);
        return userCategories.stream()
            .map(category -> CategoryResponse.createByEntity(category))
            .collect(Collectors.toList());
    }

    @Override
    public void createCategory(final String userToken, final CategoryRequest categoryRequest) {
        final User user = userAdaptor.findByUserToken(userToken).get();
        final Category category = CategoryRequest.toEntity(user, categoryRequest);

        categoryAdaptor.createCategory(category);
    }

    @Override
    public CategoryResponse modifyCategory(final String userToken, final Long categoryId, final CategoryRequest categoryRequest) {
        final Category category = categoryAdaptor.getCategoryById(categoryId)
            .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        authenticateCategory(userToken, category);

        final Category modifiedCategory = categoryRequest.modifyEntity(category);

        return CategoryResponse.createByEntity(categoryAdaptor.modify(modifiedCategory));
    }

    @Override
    public void removeCategory(final String userToken, final Long categoryId) {
        final Category category = categoryAdaptor.getCategoryById(categoryId)
            .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        authenticateCategory(userToken, category);

        categoryAdaptor.removeCategory(category);
    }

    private void authenticateCategory(final String userToken, final Category category) {
        final boolean isAuthenticated = category.authenticateUser(userToken);

        if (!isAuthenticated) {
            throw new CategoryUserAuthenticationException(ErrorCode.CATEGORY_AUTHENTICATION_ERROR);
        }
    }
}
