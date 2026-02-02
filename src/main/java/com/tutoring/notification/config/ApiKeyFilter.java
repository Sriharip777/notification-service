package com.tutoring.notification.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${security.api-key}")
    private String validApiKey;

    private static final String HEADER_NAME = "X-API-KEY";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String apiKey = request.getHeader(HEADER_NAME);

        if (apiKey == null || !apiKey.equals(validApiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing API Key");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
