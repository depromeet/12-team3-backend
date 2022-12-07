package com.depromeet.ahmatda.template;

import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.template.adaptor.TemplateAdaptor;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.template.service.impl.DeviceTemplateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<Template>> getByTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        return ResponseEntity.ok().body(RestResponse.ok(template));
    }

    @GetMapping(value = "/user/{deviceId}")
    public ResponseEntity<RestResponse<List<TemplateResponse>>> getByUserTemplates(@PathVariable String deviceId, @RequestParam("category") Long categoryId ) {
        List<TemplateResponse> template = templateService.findByCategoryAndUserId(categoryId, deviceId);
        return ResponseEntity.ok().body(RestResponse.ok(template));
    }
}
