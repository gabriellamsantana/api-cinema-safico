package br.com.cinemasafico.api.model;

import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "filmes")
public class Filme extends RepresentationModel<Filme> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A sinopse é obrigatória")
    private String sinopse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ficha_tecnica_id", referencedColumnName = "id")
    private FichaTecnica fichaTecnica;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "filme_artista",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private List<Artista> artistas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "filme_plataforma",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id")
    )
    private List<Plataforma> plataformas = new ArrayList<>();

    // --- GETTERS E SETTERS ---

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

    public List<Artista> getArtistas() { return artistas; }
    public void setArtistas(List<Artista> artistas) { this.artistas = artistas; }

    public List<Plataforma> getPlataformas() { return plataformas; }
    public void setPlataformas(List<Plataforma> plataformas) { this.plataformas = plataformas; }
}