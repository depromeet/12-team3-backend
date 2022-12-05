package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.category.CategoryController;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.user.UserController;
import com.depromeet.ahmatda.user.service.UserService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        CommonController.class,
        UserController.class,
        CategoryController.class
})
@AutoConfigureRestDocs
@ActiveProfiles("test")
public abstract class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected  ObjectMapper getObjectMapper() {
        return objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
    }

    @MockBean
    protected UserService userService;

    @MockBean
    protected CategoryService categoryService;
}
