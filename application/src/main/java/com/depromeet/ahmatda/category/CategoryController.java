package com.depromeet.ahmatda.category;

import com.depromeet.ahmatda.category.dto.CategoryRequest;
import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> getCategoryById(@PathVariable final Long id) {
        final CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponse));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getCategoriesByUserId(HttpServletRequest request) {
        final String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        final List<CategoryResponse> categoryResponses = categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getCategories() {
        final List<CategoryResponse> categoryResponses = categoryService.getCategories();
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createCategory(HttpServletRequest request, @Valid @RequestBody final CategoryRequest categoryRequest) {
        final String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        categoryService.createCategory(userId, categoryRequest);
        return ResponseEntity.ok(RestResponse.ok());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> modifyCategory(@PathVariable Long id, @RequestBody final CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.modifyCategory(id, categoryRequest);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponse));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<RestResponse<Object>> removeCategory(@PathVariable Long categoryId, HttpServletRequest request) {
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        categoryService.removeCategory(userId, categoryId);
        return ResponseEntity.ok(RestResponse.ok());
    }
}
