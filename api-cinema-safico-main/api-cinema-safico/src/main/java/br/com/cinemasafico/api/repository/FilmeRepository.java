package br.com.cinemasafico.api.repository;

import br.com.cinemasafico.api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    // Apenas herdando de JpaRepository já ganhamos findAll(), save(), findById(), delete() com paginação pronta!
}