package com.saficofilmes.api.dto;

import jakarta.validation.constraints.NotBlank;

public record PlataformaStreamingRequestDTO(
        @NotBlank(message = "O nome da plataforma é obrigatório")
        String nome,
        String urlBase,
        Boolean requerAssinatura
) {}