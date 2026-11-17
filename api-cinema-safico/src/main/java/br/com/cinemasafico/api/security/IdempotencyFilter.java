package br.com.cinemasafico.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(3)
public class IdempotencyFilter extends OncePerRequestFilter {

    private record CachedResponse(int status, String body) {}

    private final Map<String, CachedResponse> idempotencyCache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (!"POST".equalsIgnoreCase(request.getMethod())
                || !path.startsWith("/api/")
                || path.startsWith("/api/v1/auth/api-keys")) {
            filterChain.doFilter(request, response);
            return;
        }

        String idempotencyKey = request.getHeader("X-Idempotency-Key");

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": 400, \"erro\": \"O cabecalho X-Idempotency-Key e obrigatorio para operacoes POST.\"}");
            return;
        }

        String cacheKey = path + ":" + idempotencyKey;
        CachedResponse cached = idempotencyCache.get(cacheKey);

        if (cached != null) {
            response.setStatus(cached.status());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(cached.body());
            return;
        }

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(request, responseWrapper);

        int status = responseWrapper.getStatus();
        String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        if (status >= 200 && status < 300) {
            idempotencyCache.put(cacheKey, new CachedResponse(status, responseBody));
        }

        responseWrapper.copyBodyToResponse();
    }
}