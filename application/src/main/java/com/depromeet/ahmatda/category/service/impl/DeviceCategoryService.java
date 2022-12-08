package com.depromeet.ahmatda.category.service.impl;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CategoryResponse> getCategoriesByUser(final String userId) {
        final List<Category> userCategories = categoryAdaptor.getCategoriesByUserId(userId);
        return userCategories.stream()
                .map(category -> CategoryResponse.createByEntity(category))
                .collect(Collectors.toList());
    }

    @Override
    public void createCategory(final String userId, final CategoryRequest categoryRequest) {
        User user = userAdaptor.getByDeviceId(userId)
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_FOUND.getDesc()));

        Category category = CategoryRequest.toEntity(user, categoryRequest);

        categoryAdaptor.createCategory(category);
    }
}
