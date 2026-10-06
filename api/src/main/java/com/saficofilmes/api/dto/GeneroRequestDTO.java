package com.saficofilmes.api.dto;

import jakarta.validation.constraints.NotBlank;

public record GeneroRequestDTO(
        @NotBlank(message = "O nome do gênero é obrigatório")
        String nome,
        String descricao
) {}
