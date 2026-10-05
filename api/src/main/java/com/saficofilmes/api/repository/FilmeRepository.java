package com.saficofilmes.api.repository;

import com.saficofilmes.api.model.Filme;
import com.saficofilmes.api.model.TipoRepresentatividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // Consulta personalizada exigida no projeto
    @Query("SELECT DISTINCT f FROM Filme f LEFT JOIN f.diretor d LEFT JOIN f.elenco a " +
            "WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%')) " +
            "OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Filme> buscarPorArtista(@Param("nome") String nome, Pageable pageable);

    Page<Filme> findByTipoRepresentatividade(TipoRepresentatividade tipo, Pageable pageable);
}
