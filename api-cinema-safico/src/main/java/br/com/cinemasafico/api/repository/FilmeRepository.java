package br.com.cinemasafico.api.repository;

import br.com.cinemasafico.api.model.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Page<Filme> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}