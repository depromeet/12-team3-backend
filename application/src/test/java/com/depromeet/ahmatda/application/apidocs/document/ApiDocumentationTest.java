package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.category.CategoryController;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.user.UserController;
import com.depromeet.ahmatda.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        CommonController.class,
        UserController.class,
        CategoryController.class
})
@AutoConfigureRestDocs
public abstract class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected CategoryService categoryService;
}
