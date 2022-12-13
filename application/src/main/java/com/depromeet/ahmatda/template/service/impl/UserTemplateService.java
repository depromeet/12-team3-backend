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
    public void createUserTemplate(String userId, CreateTemplateRequest createTemplateRequest) {
        User user = userAdaptor.findByUserToken(userId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.AUTHENTICATION_ERROR));
        createWithUser(user, createTemplateRequest);
    }

    @Override
    @Transactional
    public void deleteUserTemplate(String userId, Long templateId) {
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.AUTHENTICATION_ERROR);
        }

        for(Item item : template.getItems()) {
            itemAdaptor.deleteItem(item);
        }

        templateAdaptor.deleteUserTemplate(template);
    }

    @Override
    @Transactional
    public TemplateResponse modfiyTemplateNameAndIsPin(String userId, ModifyTemplateRequest modifyTemplateRequest) {
          Long categoryId = modifyTemplateRequest.getCategoryId();
          Category category = categoryAdaptor.getCategoryById(categoryId)
                  .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        Long templateId = modifyTemplateRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.AUTHENTICATION_ERROR);
        }

        Template modifyTemplate = Template.modifyTemplateNameAndIsPin(template, modifyTemplateRequest.getTemplateName(), modifyTemplateRequest.isPin());

        return TemplateResponse.createByEntity(templateAdaptor.modifyTemplateNameAndIsPin(modifyTemplate));
    }

    @Override
    @Transactional
    public void templateAddItem(String userId, TemplateAddItemRequest templateAddItemRequest) {
          Long categoryId = templateAddItemRequest.getCategoryId();
          Category category = categoryAdaptor.getCategoryById(categoryId)
                  .orElseThrow(() -> new CategoryNotExistException(ErrorCode.CATEGORY_NOT_FOUND));

        Long templateId = templateAddItemRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.AUTHENTICATION_ERROR);
        }

        Item item = Item.UserTemplateAddItem(templateAddItemRequest.getCategoryId(), template,
                templateAddItemRequest.getItemName(), templateAddItemRequest.isImportant());

        itemAdaptor.createItem(item);
    }

    @Override
    @Transactional
    public void templateDeleteItem(String userId, TemplateDeleteItemRequest templateDeleteItemRequest) {
        Long templateId = templateDeleteItemRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.AUTHENTICATION_ERROR);
        }

        Long itemId = templateDeleteItemRequest.getItemId();
        Item item = itemAdaptor.findByItem(itemId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.ITEM_NOT_FOUND));

        itemAdaptor.deleteItem(item);
    }

    @Override
    @Transactional
    public void templateItemModfiy(String userId, TemplateItemModfiyRequest templateItemModfiyRequest) {
        Long templateId = templateItemModfiyRequest.getTemplateId();
        Template template = templateAdaptor.getTemplateById(templateId)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));

        boolean isAuthenticated = template.authenticateUser(userId);

        if (!isAuthenticated) {
            throw new TemplateUserAuthenticationException(ErrorCode.AUTHENTICATION_ERROR);
        }

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
}
