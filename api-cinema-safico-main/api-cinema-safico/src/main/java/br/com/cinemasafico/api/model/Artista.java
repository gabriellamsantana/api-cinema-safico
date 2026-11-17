package br.com.cinemasafico.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do artista é obrigatório")
    private String nome;

    // Aqui usamos o Enum! O Enumerated(EnumType.STRING) salva a palavra (ATOR ou DIRETOR) no banco de dados, em vez de números.
    @NotNull(message = "A função do artista é obrigatória")
    @Enumerated(EnumType.STRING)
    private FuncaoArtista funcao;

    // Relacionamento N:N com Filme (Bidirecional)
    @ManyToMany(mappedBy = "artistas")
    private List<Filme> filmes = new ArrayList<>();

    public Artista() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public FuncaoArtista getFuncao() { return funcao; }
    public void setFuncao(FuncaoArtista funcao) { this.funcao = funcao; }
    public List<Filme> getFilmes() { return filmes; }
    public void setFilmes(List<Filme> filmes) { this.filmes = filmes; }
}