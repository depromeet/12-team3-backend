package com.depromeet.ahmatda.category;

import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.category.Category;
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
    public ResponseEntity<RestResponse<Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(RestResponse.ok(category));
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<RestResponse<List<Category>>> getCategoriesByUserId(@PathVariable String userId) {
        List<Category> categories =  categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok().body(RestResponse.ok(categories));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<Category>>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok().body(RestResponse.ok(categories));
    }
}
