package com.danglinh.project_bookstore.config;


import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int statusCode = servletResponse.getStatus();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(statusCode);

        if (body instanceof String) {
            return body;
        }

        if(request.getURI().getPath().endsWith("/v3/api-docs") || request.getURI().getPath().startsWith("/swagger-ui")) {
            return body;
        }

        if (statusCode >= 400) {
            // Case error
            return body;
        } else {
            // Case success
            ApiMessage apiMessage = returnType.getMethodAnnotation(ApiMessage.class);
            if (apiMessage != null) {
                res.setMessage(apiMessage.value());
            } else {
                res.setMessage("Call API Success"); // Default message if annotation is not present
            }
            res.setData(body);
        }
        return res;
    }

}
