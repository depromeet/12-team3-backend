package com.depromeet.ahmatda.template.service;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.template.dto.TemplateAddItemsRequest;
import com.depromeet.ahmatda.template.dto.*;

import java.util.List;

public interface TemplateService {

    Template getTemplateById(Long id);

    List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userToken);

    void createUserTemplate(String userToken, CreateTemplateRequest createTemplateRequest);

    void createUserTemplate(Long userId, CreateTemplateRequest createTemplateRequest);

    void createUserTemplate(User user, CreateTemplateRequest createTemplateRequest);

    void deleteUserTemplate(String userToken, Long templateId);

    TemplateResponse modfiyTemplateNameAndIsPin(String userId, ModifyTemplateRequest modifyTemplateRequest);

    void templateAddItem(String userToken, TemplateAddItemRequest templateAddItemRequest);

    void templateAddItems(String userToken, TemplateAddItemsRequest templateAddItemsRequest);

    void templateDeleteItem(String userToken, TemplateDeleteItemRequest templateDeleteItemRequest);

    void templateItemModfiy(String userToken, TemplateItemModfiyRequest templateItemModfiyRequest);
}
