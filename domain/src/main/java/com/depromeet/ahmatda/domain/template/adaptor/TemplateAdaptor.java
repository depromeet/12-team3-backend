package com.depromeet.ahmatda.domain.template.adaptor;

import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TemplateAdaptor {

    private final TemplateRepository templateRepository;

    public Optional<Template> getTemplateById(Long templateId) {
        return templateRepository.findById(templateId);
    }

    public List<Template> findByCategoryAndUserId(Long categoryId, String userId) {
        return templateRepository.findByCategoryAndUserId(categoryId, userId);
    }

    public void createUserTemplate(Template template) {
        templateRepository.save(template);
    }

    public void deleteUserTemplate(Template template) {
        templateRepository.delete(template);
    }

    public Template modifyTemplateNameAndIsPin(Template modfiyTemplate) {
        return templateRepository.save(modfiyTemplate);
    }

    public int templatesAllOffPin(Long categoryId) {
        return templateRepository.templatesAllOffPin(categoryId);
    }

    public Boolean checkDuplicateItem(String userToken, Long templateId, Long categoryId, String itemName) {
        return templateRepository.checkDuplicateItem(userToken, templateId, categoryId, itemName);
    }
}
