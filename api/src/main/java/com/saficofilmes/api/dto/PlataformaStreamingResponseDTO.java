package com.saficofilmes.api.dto;

public record PlataformaStreamingResponseDTO(
        Long id,
        String nome,
        String urlBase,
        Boolean requerAssinatura
) {}