package com.depromeet.ahmatda.template.service;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.template.dto.*;

import java.util.List;

public interface TemplateService {

    Template getTemplateById(Long id);

    List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userId);

    void createUserTemplate(String userId, CreateTemplateRequest createTemplateRequest);

    void deleteUserTemplate(String userId, Long templateId);

    TemplateResponse modfiyTemplateNameAndIsPin(String userId, ModifyTemplateRequest modifyTemplateRequest);

    void templateAddItem(String userId, TemplateAddItemRequest templateAddItemRequest);

    void templateDeleteItem(String userId, TemplateDeleteItemRequest templateDeleteItemRequest);

    void templateItemModfiy(String userId, TemplateItemModfiyRequest templateItemModfiyRequest);
}
