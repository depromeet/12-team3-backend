package com.depromeet.ahmatda.domain.recommend.adaptor;

import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import com.depromeet.ahmatda.domain.recommend.RecommendSection;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendItemRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendSectionRepository;
import com.depromeet.ahmatda.domain.recommend.repository.RecommendTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendAdaptor {

    private final RecommendTemplateRepository recommendTemplateRepository;
    private final RecommendItemRepository recommendItemRepository;
    private final RecommendSectionRepository recommendSectionRepository;

    public List<RecommendTemplate> findByCategoryId(Long recommendCategoryId) {
        return recommendTemplateRepository.findByRecommendCategoryId(recommendCategoryId);
    }

    public RecommendSection getRandomRecommendSection() {
        return recommendSectionRepository.findRandom();
    }

    public List<RecommendItem> findAllByRecommendSectionId(Long recommendSectionId) {
        return recommendItemRepository.findAllByRecommendSectionId(recommendSectionId);
    }
}
