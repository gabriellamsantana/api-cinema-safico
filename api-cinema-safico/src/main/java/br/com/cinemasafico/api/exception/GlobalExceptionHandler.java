package br.com.cinemasafico.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Tratar erros de "Não Encontrado" (404) e Regras de Negócio (400)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> tratarErroRuntime(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // Se a mensagem for de não encontrado, devolve 404, senão devolve 400 (Bad Request)
        HttpStatus status = ex.getMessage().toLowerCase().contains("não encontrad") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        body.put("status", status.value());
        body.put("erro", ex.getMessage());

        return ResponseEntity.status(status).body(body);
    }

    // 2. Tratar erros de Validação (400 Bad Request) - Quando o @Valid reprova o JSON
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erro", "Erro de validação nos dados enviados");

        // Extrai as mensagens específicas de cada campo que falhou na validação
        List<String> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("detalhes", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 3. Tratar erro de Método HTTP incorreto (405 Method Not Allowed)
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> tratarMetodoNaoPermitido(org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put("erro", "Método HTTP não suportado para esta rota.");
        body.put("metodoEnviado", ex.getMethod());
        body.put("metodosSuportados", ex.getSupportedHttpMethods());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(body);
    }

    // 4. Tratar qualquer erro inesperado do servidor (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErrosGerais(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("erro", "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}