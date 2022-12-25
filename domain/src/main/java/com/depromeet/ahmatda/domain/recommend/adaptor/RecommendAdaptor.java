package com.depromeet.ahmatda.domain.recommend.adaptor;

import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendItemRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendAdaptor {

    private final RecommendTemplateRepository recommendTemplateRepository;
    private final RecommendItemRepository recommendItemRepository;

    public List<RecommendTemplate> findByCategoryId(Long categoryId) {
        return recommendTemplateRepository.findByCategoryId(categoryId);
    }

    public List<RecommendItem> findByItemsCategoryType(CategoryType categoryType) {
        return recommendItemRepository.findByCategoryType(categoryType);
    }
}
