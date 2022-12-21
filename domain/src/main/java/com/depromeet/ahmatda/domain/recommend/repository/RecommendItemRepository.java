package com.depromeet.ahmatda.domain.recommend.repository;

import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendItemRepository extends JpaRepository<RecommendItem, Long> {
}
