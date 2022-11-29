package com.depromeet.ahmatda.category.service;

import com.depromeet.ahmatda.domain.category.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<Category> getCategories();

    List<Category> getCategoriesByUser(String userId);
}
