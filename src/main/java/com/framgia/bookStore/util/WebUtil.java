package com.framgia.bookStore.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getHeader("Host") + request.getContextPath();
    }
}
