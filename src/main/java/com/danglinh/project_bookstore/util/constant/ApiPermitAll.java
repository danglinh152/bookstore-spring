package com.danglinh.project_bookstore.util.constant;


public class ApiPermitAll {
    // Base URL của API
    public static final String FRONT_END_URL = "http://localhost:3000/";

    // Endpoint cho phương thức GET
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/get-account",
            "/refresh",
            "/api/books/**",
            "/api/feedbacks/**",
            "/api/users?filter=**",
            "/api/account/activate",
            "/api/account/deactivate",
//            "/api/books/favorite"
    };

    // Endpoint cho phương thức POST
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/sign-in",
            "/account/register",
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

