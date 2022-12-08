package com.depromeet.ahmatda.category.service;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getCategoryById(final Long id);

    List<CategoryResponse> getCategories();

    List<CategoryResponse> getCategoriesByUser(final String userId);

    void createCategory(String userId, CategoryRequest categoryRequest);
}
