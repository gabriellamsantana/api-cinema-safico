package com.saficofilmes.api.dto;

import com.saficofilmes.api.model.TipoRepresentatividade;
import java.util.List;

public record FilmeResponseDTO(
        Long id,
        String titulo,
        String sinopse,
        Integer anoLancamento,
        Integer duracaoMinutos,
        TipoRepresentatividade tipoRepresentatividade,
        String nomeDiretor,
        List<String> nomesGeneros
) {}