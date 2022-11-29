package com.depromeet.ahmatda.domain.template.adaptor;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TemplateAdaptor {

    private final TemplateRepository templateRepository;

    public Optional<Template> getTemplateById(Long templateId) {
        return templateRepository.findById(templateId);
    }
}
