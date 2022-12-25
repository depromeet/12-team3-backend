package com.depromeet.ahmatda.domain.recommend.repository;

import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendItemRepository extends JpaRepository<RecommendItem, Long> {

    @Query(value = "select i from RecommendItem i" +
            " inner join i.category" +
            " where i.category.type = :categoryType")
    List<RecommendItem> findByCategoryType(CategoryType categoryType);
}
