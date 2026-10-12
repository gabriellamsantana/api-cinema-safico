package br.com.cinemasafico.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório e não pode estar em branco")
    private String titulo;

    private String sinopse;

    // Relacionamento 1:1 com Ficha Técnica
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ficha_tecnica_id", referencedColumnName = "id")
    private FichaTecnica fichaTecnica;

    // Relacionamento 1:N com Avaliação
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    // Relacionamento N:N com Plataforma
    @ManyToMany
    @JoinTable(
            name = "tb_filme_plataforma",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id")
    )
    private List<Plataforma> plataformas = new ArrayList<>();

    // Construtor vazio exigido pelo JPA
    public Filme() {
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public FichaTecnica getFichaTecnica() { return fichaTecnica; }
    public void setFichaTecnica(FichaTecnica fichaTecnica) { this.fichaTecnica = fichaTecnica; }

    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }

    public List<Plataforma> getPlataformas() { return plataformas; }
    public void setPlataformas(List<Plataforma> plataformas) { this.plataformas = plataformas; }
}