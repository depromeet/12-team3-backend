package com.depromeet.ahmatda.template.service;

import com.depromeet.ahmatda.domain.template.Template;

import java.util.List;

public interface TemplateService {

    Template getTemplateById(Long id);

    List<Template> findByCategoryAndUserId(Long categoryId, Long userId);
}
