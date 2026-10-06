package com.saficofilmes.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequestDTO(
        @NotBlank(message = "A fonte é obrigatória (ex: Rotten Tomatoes, Metacritic, IMDb)")
        String fonte,

        @Min(value = 0, message = "A pontuação mínima é 0")
        @Max(value = 100, message = "A pontuação máxima é 100")
        @NotNull(message = "A pontuação é obrigatória")
        Integer pontuacao,

        String consensoCritica,
        String urlOrigem,

        @NotNull(message = "O ID do filme é obrigatório")
        Long filmeId
) {}