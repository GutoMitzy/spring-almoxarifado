package com.br.almoxarifado.almoxarifado.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RateLimiterConfig rateLimiter;

    @Value("${rate-limit.capacity}")
    private long totalTokens;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String client = request.getRemoteAddr();

        if (!rateLimiter.allowRequest(client)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        response.setHeader("X-RateLimit-Remaining", String.valueOf(rateLimiter.getAvailableTokens(client)));
        response.setHeader("X-RateLimit-Limit", String.valueOf(totalTokens));

        filterChain.doFilter(request, response);
    }
}
