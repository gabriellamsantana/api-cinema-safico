package com.saficofilmes.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tb_plataforma")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlataformaStreaming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da plataforma é obrigatório")
    private String nome;

    private String urlBase;

    private Boolean requerAssinatura;
}