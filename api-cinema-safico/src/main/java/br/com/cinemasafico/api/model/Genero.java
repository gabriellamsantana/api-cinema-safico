package br.com.cinemasafico.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "generos")
@Schema(description = "Representa um gênero ou subgênero cinematográfico")
public class Genero extends RepresentationModel<Genero> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome do gênero é obrigatório")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres")
    @Schema(description = "Nome do gênero cinematográfico", example = "Drama Histórico")
    private String nome;

    @NotBlank(message = "A descrição do gênero é obrigatória")
    @Size(max = 300, message = "A descrição deve ter no máximo 300 caracteres")
    @Schema(description = "Descrição detalhada do gênero", example = "Obras ambientadas em períodos históricos passados com foco em relações sáficas.")
    private String descricao;

    public Genero() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Links getLinks() {
        return super.getLinks();
    }
}
