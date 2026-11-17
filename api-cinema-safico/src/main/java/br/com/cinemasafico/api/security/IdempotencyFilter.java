package br.com.cinemasafico.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(3)
public class IdempotencyFilter extends OncePerRequestFilter {

    private final Set<String> processedKeys = ConcurrentHashMap.newKeySet();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().startsWith("/api/")) {
            String idempotencyKey = request.getHeader("X-Idempotency-Key");

            if (idempotencyKey == null || idempotencyKey.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"erro\": \"Cabeçalho X-Idempotency-Key obrigatório para requisições POST.\"}");
                return;
            }

            if (!processedKeys.add(idempotencyKey)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"erro\": \"Conflito de idempotência: X-Idempotency-Key já processada.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}