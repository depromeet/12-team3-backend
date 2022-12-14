package com.depromeet.ahmatda.application.apidocs.document;

import com.depromeet.ahmatda.alarm.history.controller.UserAlarmHistoryController;
import com.depromeet.ahmatda.alarm.history.service.AlarmMessageHistoryService;
import com.depromeet.ahmatda.alarm.service.AlarmService;
import com.depromeet.ahmatda.alarm.controller.UserAlarmController;
import com.depromeet.ahmatda.fcm.service.impl.FcmPushService;
import com.depromeet.ahmatda.fcm.controller.FcmTokenController;
import com.depromeet.ahmatda.fcm.service.FcmTokenService;
import com.depromeet.ahmatda.category.CategoryController;
import com.depromeet.ahmatda.category.service.CategoryService;
import com.depromeet.ahmatda.recommend.RecommendController;
import com.depromeet.ahmatda.recommend.service.RecommendService;
import com.depromeet.ahmatda.template.TemplateController;
import com.depromeet.ahmatda.template.service.TemplateService;
import com.depromeet.ahmatda.user.UserController;
import com.depromeet.ahmatda.user.service.UserService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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
        TemplateController.class,
        RecommendController.class,
        UserAlarmController.class,
        FcmTokenController.class,
        UserAlarmHistoryController.class
})
@AutoConfigureRestDocs
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ApiDocumentationTest {

    @BeforeAll
    void init() {
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
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

    @MockBean
    protected RecommendService recommendService;

    @MockBean
    protected AlarmService alarmService;

    @MockBean
    protected FcmTokenService fcmTokenService;

    @MockBean
    protected FcmPushService fcmPushService;

    @MockBean
    protected AlarmMessageHistoryService alarmMessageHistoryService;
}
