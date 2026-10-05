package com.saficofilmes.api.repository;

import com.saficofilmes.api.model.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Consulta personalizada exigida no projeto
    Page<Genero> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}