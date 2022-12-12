package com.depromeet.ahmatda.template;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.template.dto.*;
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
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        List<TemplateResponse> template = templateService.findByCategoryAndUserId(categoryId, userId);
        return ResponseEntity.ok().body(RestResponse.ok(template));
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createUserTemplate(HttpServletRequest request, @RequestBody CreateTemplateRequest createTemplateRequest) {
        final String userId = request.getHeader(HttpHeader.USER_TOKEN);
        templateService.createUserTemplate(userId, createTemplateRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<RestResponse<Object>> deleteUserTemplate(HttpServletRequest request, @PathVariable Long templateId) {
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        templateService.deleteUserTemplate(userId, templateId);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @PatchMapping
    public ResponseEntity<RestResponse<TemplateResponse>> modfiyTemplateNameAndIsPin(HttpServletRequest request, @RequestBody ModifyTemplateRequest modifyTemplateRequest) {
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        TemplateResponse templateResponse = templateService.modfiyTemplateNameAndIsPin(userId, modifyTemplateRequest);
        return ResponseEntity.ok().body(RestResponse.ok(templateResponse));
    }

    @PostMapping("/item")
    public ResponseEntity<RestResponse<Object>> templateAddItem(HttpServletRequest request, @RequestBody TemplateAddItemRequest templateAddItemRequest) {
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        templateService.templateAddItem(userId, templateAddItemRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }

    @DeleteMapping("/item")
    public ResponseEntity<RestResponse<Object>> templateDeleteItem(HttpServletRequest request, @RequestBody TemplateDeleteItemRequest templateDeleteItemRequest) {
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        templateService.templateDeleteItem(userId, templateDeleteItemRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }

    @PatchMapping("/item")
    public ResponseEntity<RestResponse<Object>> templateItemModfiy(HttpServletRequest request, @RequestBody TemplateItemModfiyRequest templateItemModfiyRequest) {
        String userId = request.getHeader(HttpHeader.USER_TOKEN);
        templateService.templateItemModfiy(userId, templateItemModfiyRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}