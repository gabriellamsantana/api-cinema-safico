package com.saficofilmes.api.repository;

import com.saficofilmes.api.model.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    // Consulta personalizada exigida no projeto
    Page<Artista> findByNacionalidadeIgnoreCase(String nacionalidade, Pageable pageable);
}