package com.saficofilmes.api.dto;

import java.time.LocalDate;

public record ArtistaResponseDTO(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade,
        String biografia
) {}
