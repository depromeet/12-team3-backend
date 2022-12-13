package com.depromeet.ahmatda.category.service;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.user.User;

import java.util.List;

public interface CategoryService {

    CategoryResponse getCategoryById(final Long id);

    List<CategoryResponse> getCategories();

    List<CategoryResponse> getCategoriesByUser(final String userToken);

    Category createCategory(User user, CategoryRequest categoryRequest);
    void createCategory(String userId, CategoryRequest categoryRequest);

    CategoryResponse modifyCategory(String userToken, Long categoryId, CategoryRequest categoryRequest);

    void removeCategory(String userToken, Long categoryId);
}
