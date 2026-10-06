package com.saficofilmes.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_artista")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do artista é obrigatório")
    private String nome;

    private LocalDate dataNascimento;

    private String nacionalidade;

    @Column(length = 1000)
    private String biografia;
}