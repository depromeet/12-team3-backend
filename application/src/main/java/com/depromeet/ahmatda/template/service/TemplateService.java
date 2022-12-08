package com.depromeet.ahmatda.template.service;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.template.dto.TemplateResponse;

import java.util.List;

public interface TemplateService {

    Template getTemplateById(Long id);

    List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userId);
}
