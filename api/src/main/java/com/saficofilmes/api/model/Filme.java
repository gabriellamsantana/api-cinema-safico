package com.saficofilmes.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_filme")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A sinopse é obrigatória")
    @Column(length = 2000)
    private String sinopse;

    @NotNull(message = "O ano de lançamento é obrigatório")
    @Min(value = 1888, message = "Ano de lançamento inválido")
    private Integer anoLancamento;

    @NotNull(message = "A duração é obrigatória")
    @Positive(message = "A duração deve ser maior que zero")
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de representatividade é obrigatório")
    private com.saficofilmes.api.model.TipoRepresentatividade tipoRepresentatividade;

    // Relacionamento OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plataforma_id", referencedColumnName = "id")
    private com.saficofilmes.api.model.PlataformaStreaming plataformaPrincipal;

    // Relacionamento ManyToOne (Direção)
    @ManyToOne
    @JoinColumn(name = "diretor_id")
    private com.saficofilmes.api.model.Artista diretor;

    // Relacionamento ManyToMany (Elenco / Atrizes)
    @ManyToMany
    @JoinTable(
            name = "tb_filme_atriz",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private List<com.saficofilmes.api.model.Artista> elenco = new ArrayList<>();

    // Relacionamento ManyToMany (Gêneros)
    @ManyToMany
    @JoinTable(
            name = "tb_filme_genero",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<com.saficofilmes.api.model.Genero> generos = new ArrayList<>();

    // Relacionamento OneToMany (Avaliações dos agregadores)
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.saficofilmes.api.model.Avaliacao> avaliacoes = new ArrayList<>();
}