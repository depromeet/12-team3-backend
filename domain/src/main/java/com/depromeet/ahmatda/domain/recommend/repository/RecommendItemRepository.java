package com.depromeet.ahmatda.domain.recommend.repository;

import com.depromeet.ahmatda.domain.recommend.RecommendItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendItemRepository extends JpaRepository<RecommendItem, Long> {

    @Query(value = "select distinct ri from RecommendItem ri" +
            " join fetch ri.recommendSection rs" +
            " where ri.recommendSection.id = rs.id and rs.id = :recommendSectionId")
    List<RecommendItem> findAllByRecommendSectionId(Long recommendSectionId);
}
