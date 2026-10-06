package com.saficofilmes.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tb_avaliacao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A fonte é obrigatória (ex: Rotten Tomatoes, Metacritic, IMDb)")
    private String fonte;

    @Min(value = 0, message = "A pontuação mínima é 0")
    @Max(value = 100, message = "A pontuação máxima é 100")
    @NotNull(message = "A pontuação é obrigatória")
    private Integer pontuacao;

    private String consensoCritica;

    private String urlOrigem;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private com.saficofilmes.api.model.Filme filme;
}
