package com.danglinh.project_bookstore.config;


import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.danglinh.project_bookstore.util.constant.ApiPermitAll.PUBLIC_GET_ENDPOINTS;
import static com.danglinh.project_bookstore.util.constant.ApiPermitAll.PUBLIC_POST_ENDPOINTS;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
    String[] whiteList = {
            "/auth/get-account",
            "/auth/refresh",
            "/auth/activate",
            "/auth/get-activate",
            "/auth/deactivate",
//            "/api/books/**",
//            "/api/feedbacks/**",
//            "/api/users?filter=**",
//            "/api/books/favorite"
//            "/api/books",
            "/auth/sign-out",
            "/auth/register",
//            "/api/book/feedback/givefeedback",
//            "/api/book/favorite"
//            "/api/users",
//            "/api/users/**"
    };

    @Bean
    PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor())
                .excludePathPatterns(whiteList);
    }
}
