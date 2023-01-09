package com.depromeet.ahmatda.domain.recommend.repository;

import com.depromeet.ahmatda.domain.recommend.RecommendSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecommendSectionRepository extends JpaRepository<RecommendSection, Long> {

    List<RecommendSection> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM recommend_section rs JOIN recommend_category rc " +
            "ON rs.recommend_category_id = rc.id WHERE rc.type = :categoryType ORDER BY rand() LIMIT 1;")
    RecommendSection findRandom(String categoryType);
}
