package com.depromeet.ahmatda.template.service.impl;

import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import com.depromeet.ahmatda.domain.item.adaptor.ItemAdaptor;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import com.depromeet.ahmatda.template.exception.TemplateUserAuthenticationException;
import com.depromeet.ahmatda.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceTemplateService implements TemplateService {

    private final TemplateAdaptor templateAdaptor;
    private final UserAdaptor userAdaptor;
    private final CategoryAdaptor categoryAdaptor;
    private final ItemAdaptor itemAdaptor;

    @Override
    public Template getTemplateById(Long id) {
        return templateAdaptor.getTemplateById(id)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));
    }

    @Override
    public List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userId) {
        List<Template> templates = templateAdaptor.findByCategoryAndUserId(categoryId, userId);
        return templates.stream()
                .map(template -> TemplateResponse.createByEntity(template))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createUserTemplate(String userId, CreateTemplateRequest createTemplateRequest) {
        //TODO:유저 검증 수정 필요, Exception 처리필요
        User user = userAdaptor.findByUserToken(userId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.BINDING_ERROR));

        Long categoryId = createTemplateRequest.getCategoryId();
        Category category = categoryAdaptor.getCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        Template template = Template.createTemplate(createTemplateRequest.getTemplateName(), category, user);

        templateAdaptor.createUserTemplate(template);

        if(createTemplateRequest.getItems() != null) {
            for (TemplateItemRequest itemRequest : createTemplateRequest.getItems()) {
                Item item = Item.createItem(createTemplateRequest.getCategoryId(), template, itemRequest.getName());
                itemAdaptor.createItem(item);
            }
        }

    }

    @Override
    @Transactional
    public void deleteUserTemplate(String userId, Long templateId) {
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            //TODO: errorcode 수정필요
            throw new TemplateUserAuthenticationException(ErrorCode.BINDING_ERROR);
        }

        for(Item item : template.getItems()) {
            itemAdaptor.deleteItem(item);
        }

        templateAdaptor.deleteUserTemplate(template);
    }
}