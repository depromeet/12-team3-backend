package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.category.CategoryController;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.template.TemplateController;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.UserController;
import com.depromeet.ahmatda.user.service.UserService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        CommonController.class,
        UserController.class,
        CategoryController.class,
        TemplateController.class
})
@AutoConfigureRestDocs
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ApiDocumentationTest {

    @BeforeAll
    void init() {
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected CategoryService categoryService;

    @MockBean
    protected TemplateService templateService;
}
