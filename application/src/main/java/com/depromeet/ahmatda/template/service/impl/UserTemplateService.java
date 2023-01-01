package com.depromeet.ahmatda.template.service.impl;

import com.depromeet.ahmatda.alarm.AlarmService;
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
import com.depromeet.ahmatda.template.dto.TemplateAddItemsRequest;
import com.depromeet.ahmatda.template.dto.*;
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
public class UserTemplateService implements TemplateService {

    private final TemplateAdaptor templateAdaptor;
    private final UserAdaptor userAdaptor;
    private final CategoryAdaptor categoryAdaptor;
    private final ItemAdaptor itemAdaptor;
    private final AlarmService alarmService;

    @Override
    public Template getTemplateById(Long id) {
        return templateAdaptor.getTemplateById(id)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));
    }

    @Override
    public List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String userId) {
        List<Template> templates = templateAdaptor.findByCategoryAndUserId(categoryId, userId);
        return templates.stream()
                .map(template -> {
                    String alarmInfo = alarmService.getAlarmInfo(Long.getLong(userId), template.getId());
                    return TemplateResponse.createByEntity(template, alarmInfo);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createUserTemplate(User user, CreateTemplateRequest createTemplateRequest) {
        createWithUser(user, createTemplateRequest);
    }

    private void createWithUser(User user, CreateTemplateRequest createTemplateRequest) {
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
    public void createUserTemplate(String userToken, CreateTemplateRequest createTemplateRequest) {
        User user = userAdaptor.findByUserToken(userToken)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.AUTHENTICATION_ERROR));
        createWithUser(user, createTemplateRequest);
    }

    @Override
    @Transactional
    public void createUserTemplate(Long userId, CreateTemplateRequest createTemplateRequest) {
        User user = userAdaptor.findUserById(userId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.AUTHENTICATION_ERROR));
        createWithUser(user, createTemplateRequest);
    }

    @Override
    @Transactional
    public void deleteUserTemplate(String userToken, Long templateId) {
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        for(Item item : template.getItems()) {
            itemAdaptor.deleteItem(item);
        }

        templateAdaptor.deleteUserTemplate(template);
    }

    @Override
    @Transactional
    public TemplateResponse modfiyTemplateNameAndIsPin(String userToken, ModifyTemplateRequest modifyTemplateRequest) {
        Long templateId = modifyTemplateRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        if (modifyTemplateRequest.getTemplateName() == null) {
            templatesAllOffPin(modifyTemplateRequest.getCategoryId(), modifyTemplateRequest.isPin());
            template.modifyTemplateIsPIn(modifyTemplateRequest.isPin());
        } else {
            template.modifyTemplateName(modifyTemplateRequest.getTemplateName());
        }

        return TemplateResponse.createByEntity(templateAdaptor.modifyTemplateNameAndIsPin(template), null);
    }

    @Override
    @Transactional
    public void templateAddItem(String userToken, TemplateAddItemRequest templateAddItemRequest) {
        Long templateId = templateAddItemRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        Item item = Item.UserTemplateAddItem(templateAddItemRequest.getCategoryId(), template,
                templateAddItemRequest.getItemName(), templateAddItemRequest.isImportant());

        itemAdaptor.createItem(item);
    }

    @Override
    @Transactional
    public void templateAddItems(String userToken, TemplateAddItemsRequest templateAddItemsRequest) {
        Long templateId = templateAddItemsRequest.getUserTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        List<Item> items = templateAddItemsRequest.getItems().stream()
                .map(itemName -> Item.UserTemplateAddItem(templateAddItemsRequest.getUserCategoryId(), template, itemName, false))
                .collect(Collectors.toList());

        itemAdaptor.createAllItem(items);

    }

    @Override
    @Transactional
    public void templateDeleteItem(String userToken, TemplateDeleteItemRequest templateDeleteItemRequest) {
        Long templateId = templateDeleteItemRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        Long itemId = templateDeleteItemRequest.getItemId();
        Item item = itemAdaptor.findByItem(itemId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.ITEM_NOT_FOUND));

        itemAdaptor.deleteItem(item);
    }

    @Override
    @Transactional
    public void templateItemModfiy(String userToken, TemplateItemModfiyRequest templateItemModfiyRequest) {
        Long templateId = templateItemModfiyRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        authenticateTemplate(userToken, template);

        Long itemId = templateItemModfiyRequest.getItemId();
        Item item = itemAdaptor.findByItem(itemId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.ITEM_NOT_FOUND));

        if(templateItemModfiyRequest.getIsTake() != null) {
            Item.modifyItemIsTack(item, templateItemModfiyRequest.getIsTake());
        } else {
            Item.modfiyItemNameAndIsImportant(item, templateItemModfiyRequest.getModifiedItemName(), templateItemModfiyRequest.isImportant());
        }
        itemAdaptor.modfiyItem(item);
    }

    private void authenticateTemplate(String userToken, Template template) {
        final boolean isAuthenticated = template.authenticateUser(userToken);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private void templatesAllOffPin(Long categoryId, boolean isPin) {
        if (isPin == true) {
            templateAdaptor.templatesAllOffPin(categoryId);
        }
    }
}
