package br.com.cinemasafico.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tb_plataforma")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da plataforma é obrigatório")
    private String nome;

    // Relacionamento Muitos-para-Muitos: Uma plataforma tem vários filmes, um filme está em várias plataformas.
    // O 'mappedBy' indica que a relação será gerenciada pela entidade Filme.
    @ManyToMany(mappedBy = "plataformas")
    private List<Filme> filmes = new ArrayList<>();

    public Plataforma() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Filme> getFilmes() { return filmes; }
    public void setFilmes(List<Filme> filmes) { this.filmes = filmes; }
}
