package com.depromeet.ahmatda.template.service.impl;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.template.exception.TemplateNotExistException;
import com.depromeet.ahmatda.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceTemplateService implements TemplateService {

    private final TemplateAdaptor templateAdaptor;

    @Override
    public Template getTemplateById(Long id) {
        return templateAdaptor.getTemplateById(id)
                .orElseThrow(() -> new TemplateNotExistException(ErrorCode.TEMPLATE_NOT_FOUND));
    }

    @Override
    public List<TemplateResponse> findByCategoryAndUserId(Long categoryId, String deviceId) {
        List<Template> templates = templateAdaptor.findByCategoryAndUserId(categoryId, deviceId);
        return templates.stream()
                .map(template -> TemplateResponse.createByEntity(template))
                .collect(Collectors.toList());
    }
}
