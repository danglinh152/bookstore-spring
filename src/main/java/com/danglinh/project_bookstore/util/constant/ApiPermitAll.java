package com.danglinh.project_bookstore.util.constant;


public class ApiPermitAll {
    // Base URL của API
    public static final String FRONT_END_URL = "http://localhost:5173/";

    // Endpoint cho phương thức GET
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/auth/get-account",
            "/auth/refresh",
            "/auth/activate",
            "/auth/get-activate",
            "/auth/deactivate",
            "/api/books/**",
            "/api/feedbacks/**",
            "/api/users?filter=**",
            "api/books"
//            "/api/books/favorite"
    };

    // Endpoint cho phương thức POST
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/auth/sign-in",
            "/auth/sign-out",
            "/auth/register",
//            "/api/book/feedback/givefeedback",
//            "/api/book/favorite"
    };

    public static final String[] PUBLIC_DELETE_ENDPOINTS = {
//            "/api/book/favorite"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/users",
            "/api/users/**"
    };
}

