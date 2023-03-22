package com.example.backend_project.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class LocaleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String lang = request.getHeader("Accept-Language");
        Locale locale = Locale.getDefault();
        if (lang != null && !lang.isEmpty()) {
            locale = Locale.forLanguageTag(lang);
        }
        LocaleContextHolder.setLocale(locale);
        return true;
    }
}
