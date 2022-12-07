package com.depromeet.ahmatda.category.service;

import com.depromeet.ahmatda.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getCategories();

    List<CategoryResponse> getCategoriesByUser(String userId);
}
