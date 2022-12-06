package com.depromeet.ahmatda.category;

import com.depromeet.ahmatda.category.dto.CategoryResponse;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.Category;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponse));
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<RestResponse<List<Category>>> getCategoriesByUserId(@PathVariable String userId) {
        List<Category> categories =  categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok().body(RestResponse.ok(categories));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getCategories();
        return ResponseEntity.ok().body(RestResponse.ok(categoryResponses));
    }
}
