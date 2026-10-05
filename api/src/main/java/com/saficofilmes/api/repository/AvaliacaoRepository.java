package com.saficofilmes.api.repository;

import com.saficofilmes.api.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Consulta personalizada exigida no projeto
    Page<Avaliacao> findByPontuacaoGreaterThanEqual(Integer pontuacaoMinima, Pageable pageable);
}