package com.depromeet.ahmatda.domain.recommend.repository;

import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendTemplateRepository extends JpaRepository<RecommendTemplate, Long> {

    @Query(value = "select distinct t from RecommendTemplate t" +
            " join fetch t.recommendItems" +
            " where t.category.id = :categoryId")
    List<RecommendTemplate> findByCategory_Id(Long categoryId);
}
