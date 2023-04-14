package com.github.yvasyliev.uitls;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static String getHost(HttpServletRequest request) {
        return request
                .getRequestURL()
                .toString()
                .replace(request.getRequestURI(), "");
    }
}
