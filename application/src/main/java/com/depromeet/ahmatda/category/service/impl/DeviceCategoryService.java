package com.depromeet.ahmatda.category.service.impl;

import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceCategoryService implements CategoryService {

    private final CategoryAdaptor categoryAdaptor;

    @Override
    public Category getCategoryById(Long id) {
        return categoryAdaptor.getCategoryById(id)
                .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<Category> getCategories() {
        return categoryAdaptor.getCategories();
    }

    @Override
    public List<Category> getCategoriesByUser(String deviceId) {
        return categoryAdaptor.getCategoriesByDeviceId(deviceId);
    }
}
