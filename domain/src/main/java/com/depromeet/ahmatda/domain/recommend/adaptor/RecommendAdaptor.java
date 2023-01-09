package com.depromeet.ahmatda.domain.recommend.adaptor;

import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.recommend.RecommendCategory;
import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import com.depromeet.ahmatda.domain.recommend.RecommendSection;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendCategoryRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendItemRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendSectionRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendAdaptor {

    private final RecommendCategoryRepository recommendCategoryRepository;
    private final RecommendTemplateRepository recommendTemplateRepository;
    private final RecommendSectionRepository recommendSectionRepository;
    private final RecommendItemRepository recommendItemRepository;

    public List<RecommendCategory> getAllRecommendCategory() {
        return recommendCategoryRepository.findAll();
    }

    public List<RecommendTemplate> getRtsByRcId(Long recommendCategoryId) {
        return recommendTemplateRepository.findByRecommendCategoryId(recommendCategoryId);
    }

    public RecommendSection getRandomRecommendSection(CategoryType categoryType) {
        return recommendSectionRepository.findRandom(categoryType.name());
    }

    public List<RecommendItem> getRiByRs(Long recommendSectionId) {
        return recommendItemRepository.findAllByRecommendSectionId(recommendSectionId);
    }
}
