package com.depromeet.ahmatda.domain.template.repository;

import com.depromeet.ahmatda.domain.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByCategoryAndUserId(Long categoryId, Long userId);
}
