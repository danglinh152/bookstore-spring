package com.danglinh.project_bookstore.security;

import java.util.ArrayList;

public class Endpoints {
    public static final String front_end_port = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/books/**",
            "/feedbacks/**",
            "/users/search/existsByUsername",
            "/users/search/existsByEmail",
            "/account/activate"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",
            "/users/**"
    };

}
