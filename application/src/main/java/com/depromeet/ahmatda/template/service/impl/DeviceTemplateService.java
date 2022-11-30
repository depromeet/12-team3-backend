package com.depromeet.ahmatda.template.service.impl;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import com.depromeet.ahmatda.template.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceTemplateService implements TemplateService {

    TemplateAdaptor templateAdaptor;

    @Override
    public Template getTemplateById(Long id) {
        return templateAdaptor.getTemplateById(id)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));
    }

    @Override
    public List<Template> findByCategoryAndUserId(Long categoryId, Long userId) {
        return templateAdaptor.findByCategoryAndUserId(categoryId, userId);
    }
}
