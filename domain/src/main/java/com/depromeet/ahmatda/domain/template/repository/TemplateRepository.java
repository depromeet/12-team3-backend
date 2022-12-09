package com.depromeet.ahmatda.domain.template.repository;

import com.depromeet.ahmatda.domain.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query(value = "select t from Template t" +
            " join fetch t.user" +
            " join fetch t.items" +
            " where t.category.id = :categoryId and t.user.userToken = :userId")
    List<Template> findByCategoryAndUserId(Long categoryId, String userId);
}
