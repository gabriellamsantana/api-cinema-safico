package br.com.cinemasafico.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório e não pode estar em branco")
    private String titulo;

    private String sinopse;

    // Relacionamento 1:1. O CascadeType.ALL faz com que ao salvar o Filme, a Ficha Técnica seja salva junto automaticamente.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ficha_tecnica_id", referencedColumnName = "id")
    private FichaTecnica fichaTecnica;

    // Construtor vazio
    public Filme() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public FichaTecnica getFichaTecnica() { return fichaTecnica; }
    public void setFichaTecnica(FichaTecnica fichaTecnica) { this.fichaTecnica = fichaTecnica; }
}
