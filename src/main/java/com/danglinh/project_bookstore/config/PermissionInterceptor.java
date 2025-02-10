package com.danglinh.project_bookstore.config;

import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.UserService;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.danglinh.project_bookstore.util.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IdInvalidException {
        String path = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        String username = SecurityUtil.getCurrentUser().isPresent() ? SecurityUtil.getCurrentUser().get() : null;
        User user = userService.findUserByUsername(username);
        if (user != null) {
            Role role = user.getRole();
            if (role != null) {
                List<Permission> listOfPermission = role.getListOfPermissions();
                boolean isAllow = listOfPermission.stream().anyMatch(item -> item.getApiPath().equals(path) && item.getMethod().equals(httpMethod));
                if (!isAllow) {
                    throw new IdInvalidException("You don't have permission");
                }
            } else {
                throw new IdInvalidException("You don't have role");
            }
        }
        return true;
    }
}
