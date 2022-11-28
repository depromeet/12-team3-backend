package com.depromeet.ahmatda.domain.category.adaptor;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryAdaptor {

    private final CategoryRepository categoryRepository;

    public Optional<Category> getByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
