package com.br.almoxarifado.almoxarifado.config;

import com.br.almoxarifado.almoxarifado.enums.RolesEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RateLimiterConfig rateLimiter;

    @Value("${rate-limit.capacity}")
    private long totalTokens;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("Auth no rate limiter: {}", SecurityContextHolder.getContext().getAuthentication());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(isAdmin(authentication)) {
            filterChain.doFilter(request, response);
            return;
        }

        String client = resolveClientKey(request, authentication);

        if (!rateLimiter.allowRequest(client)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        response.setHeader("X-RateLimit-Remaining", String.valueOf(rateLimiter.getAvailableTokens(client)));
        response.setHeader("X-RateLimit-Limit", String.valueOf(totalTokens));

        filterChain.doFilter(request, response);
    }

    public boolean isAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(a -> RolesEnum.ROLE_ADMIN.name().equals(a.getAuthority()));
    }

    private String resolveClientKey(HttpServletRequest request, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "user:" + authentication.getName();
        }

        return "ip:" + request.getRemoteAddr();
    }
}
