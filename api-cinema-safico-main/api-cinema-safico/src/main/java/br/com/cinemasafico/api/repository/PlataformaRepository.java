package br.com.cinemasafico.api.repository;

import br.com.cinemasafico.api.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {
}