package com.suivi_academique.config;


import com.suivi_academique.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
//@Order(1)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Request ID
        String requestId = UUID.randomUUID().toString().substring(0, 8);

        // 2. IP client
        String clientIP = getClientIpAddress(request);

        // 3. ID utilisateur (si connecté)
        String userId = "ANONYME";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails customUser) {
            userId = customUser.getCodePersonnel();
        }

        // MDC pour TOUS les logs
        MDC.put("requestId", requestId);
        MDC.put("clientIP", clientIP);
        MDC.put("userId", userId);

        log.info("🚀 {} {} | IP:{} | User:{} | ID:{}",
                request.getMethod(), request.getRequestURI(), clientIP, userId, requestId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();  // Nettoyage
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
