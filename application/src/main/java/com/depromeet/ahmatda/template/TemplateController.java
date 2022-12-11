package com.depromeet.ahmatda.template;

import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.template.dto.CreateTemplateRequest;
import com.depromeet.ahmatda.template.dto.ModifyTemplateRequest;
import com.depromeet.ahmatda.template.dto.TemplateAddItemRequest;
import com.depromeet.ahmatda.template.dto.TemplateResponse;
import com.depromeet.ahmatda.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "/user")
    public ResponseEntity<RestResponse<List<TemplateResponse>>> getByUserTemplates(HttpServletRequest request, @RequestParam("category") Long categoryId ) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        List<TemplateResponse> template = templateService.findByCategoryAndUserId(categoryId, userId);
        return ResponseEntity.ok().body(RestResponse.ok(template));
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createUserTemplate(HttpServletRequest request, @RequestBody CreateTemplateRequest createTemplateRequest) {
        final String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        templateService.createUserTemplate(userId, createTemplateRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<RestResponse<Object>> deleteUserTemplate(HttpServletRequest request, @PathVariable Long templateId) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        templateService.deleteUserTemplate(userId, templateId);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @PatchMapping
    public ResponseEntity<RestResponse<TemplateResponse>> modfiyTemplateNameAndIsPin(HttpServletRequest request, @RequestBody ModifyTemplateRequest modifyTemplateRequest) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        TemplateResponse templateResponse = templateService.modfiyTemplateNameAndIsPin(userId, modifyTemplateRequest);
        return ResponseEntity.ok().body(RestResponse.ok(templateResponse));
    }

    @PostMapping("/item")
    public ResponseEntity<RestResponse<Object>> templateAddItem(HttpServletRequest request, @RequestBody TemplateAddItemRequest templateAddItemRequest) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        templateService.templateAddItem(userId, templateAddItemRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }

    @DeleteMapping("/{templateId}/item/{itemId}")
    public ResponseEntity<RestResponse<Object>> templateDeleteItem(HttpServletRequest request, @PathVariable Long templateId, @PathVariable Long itemId) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        templateService.templateDeleteItem(userId, templateId, itemId);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}
