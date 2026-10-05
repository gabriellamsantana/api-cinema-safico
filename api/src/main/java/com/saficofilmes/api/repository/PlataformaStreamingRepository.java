package com.saficofilmes.api.repository;

import com.saficofilmes.api.model.PlataformaStreaming;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlataformaStreamingRepository extends JpaRepository<PlataformaStreaming, Long> {
    // Consulta personalizada exigida no projeto
    Page<PlataformaStreaming> findByRequerAssinatura(Boolean requerAssinatura, Pageable pageable);
}