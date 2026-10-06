package com.saficofilmes.api.dto;

import com.saficofilmes.api.model.TipoRepresentatividade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record FilmeRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A sinopse é obrigatória")
        String sinopse,

        @NotNull(message = "O ano de lançamento é obrigatório")
        @Min(value = 1888, message = "Ano de lançamento inválido")
        Integer anoLancamento,

        @NotNull(message = "A duração é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer duracaoMinutos,

        @NotNull(message = "O tipo de representatividade é obrigatório")
        TipoRepresentatividade tipoRepresentatividade,

        // Recebemos apenas os IDs dos relacionamentos para facilitar a criação
        Long diretorId,
        List<Long> generosIds,
        List<Long> elencoIds
) {}