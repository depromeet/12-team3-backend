package com.depromeet.ahmatda.template.service.impl;

import com.depromeet.ahmatda.category.exception.CategoryNotExistException;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.Item;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.category.adaptor.CategoryAdaptor;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemRequest;
import com.depromeet.ahmatda.template.dto.TemplateItemResponse;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceTemplateService implements TemplateService {

    private final TemplateAdaptor templateAdaptor;
    private final UserAdaptor userAdaptor;
    private final CategoryAdaptor categoryAdaptor;

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
    public void createUserTemplate(String userId, CreateTemplateRequest createTemplateRequest) {
        //TODO:유저 검증 수정 필요
        User user = userAdaptor.getByDeviceId(userId)
                .orElseThrow(() -> new UserNotExistException(ErrorCode.BINDING_ERROR.getDesc()));

        Category category = categoryAdaptor.getCategoryById(createTemplateRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        Template template = Template.createTemplate(createTemplateRequest.getTemplateName(), category, user);

        if(createTemplateRequest.getItems().size() > 0) {
            for(TemplateItemRequest itemRequest : createTemplateRequest.getItems()){
                Item item = Item.createItem(createTemplateRequest.getCategoryId(), template, itemRequest.getName());
                template.addUserTemplateItem(item);
            }
        }

        templateAdaptor.createUserTemplate(template);

    }
}
