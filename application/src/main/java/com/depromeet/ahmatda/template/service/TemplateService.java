package com.depromeet.ahmatda.template.service;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateResponse;

import java.util.List;

public interface TemplateService {

    Template getTemplateById(Long id);

    List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userId);

    void createUserTemplate(User user, CreateTemplateRequest createTemplateRequest);
    void createUserTemplate(String userId, CreateTemplateRequest createTemplateRequest);

    void deleteUserTemplate(String userId, Long templateId);
}
