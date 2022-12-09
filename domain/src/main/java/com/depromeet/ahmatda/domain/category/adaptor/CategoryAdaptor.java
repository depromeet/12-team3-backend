package com.depromeet.ahmatda.domain.category.adaptor;

import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryAdaptor {

    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategoryById(final Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Category> getCategoriesByUserId(final String userId) {
        return categoryRepository.findAllByUserId(userId);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category modify(Category category) {
        return categoryRepository.save(category);
    }

    public void removeCategory(Category category) {
        categoryRepository.delete(category);
    }
}
