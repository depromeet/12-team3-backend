package com.depromeet.ahmatda.category;

import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.Category;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        String userId = request.getHeader(HttpHeader.USER_ID_KEY);
        final List<CategoryResponse> categoryResponses =  categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getCategories() {
        final List<CategoryResponse> categoryResponses = categoryService.getCategories();
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }
}
