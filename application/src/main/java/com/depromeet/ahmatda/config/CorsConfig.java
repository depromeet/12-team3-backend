package com.depromeet.ahmatda.config;

import com.depromeet.ahmatda.HttpHeader;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://*.12-team3-web.pages.dev")
                .allowedOrigins("https://12-team3-web.pages.dev")
                .allowedHeaders(HttpHeader.USER_TOKEN)
                .allowedMethods(HttpMethod.GET.name())
                .allowedMethods(HttpMethod.POST.name())
                .allowedMethods(HttpMethod.DELETE.name())
                .allowedMethods(HttpMethod.PATCH.name());
    }
}
