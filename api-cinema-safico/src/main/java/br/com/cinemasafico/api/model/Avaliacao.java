package br.com.cinemasafico.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A fonte da avaliação não pode estar em branco")
    private String fonte; // Ex: Rotten Tomatoes, IMDb

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 0, message = "A nota não pode ser menor que zero")
    @Max(value = 100, message = "A nota máxima é 100")
    private Integer nota;

    private String comentario;

    // Relacionamento Muitos-para-Um: Muitas avaliações pertencem a um Filme
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    public Avaliacao() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
}
