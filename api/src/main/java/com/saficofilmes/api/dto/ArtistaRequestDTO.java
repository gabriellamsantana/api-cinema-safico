package com.saficofilmes.api.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ArtistaRequestDTO(
        @NotBlank(message = "O nome do artista é obrigatório")
        String nome,
        LocalDate dataNascimento,
        String nacionalidade,
        String biografia
) {}