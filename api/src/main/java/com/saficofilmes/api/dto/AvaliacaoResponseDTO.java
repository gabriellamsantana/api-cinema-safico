package com.saficofilmes.api.dto;

public record AvaliacaoResponseDTO(
        Long id,
        String fonte,
        Integer pontuacao,
        String consensoCritica,
        String urlOrigem,
        Long filmeId,
        String filmeTitulo
) {}
