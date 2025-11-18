package com.carehub.carehub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa a entidade Médico no sistema CareHub.
 * Mapeada para a tabela "medicos".
 */
@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeCompleto;

    @Column(nullable = false, length = 20, unique = true)
    private String crm; // Registro do Conselho Regional de Medicina

    @Column(nullable = false, length = 15)
    private String telefone;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    // Mapeamento do Enum: Armazena o nome da constante (ex: 'CARDIOLOGIA') no DB.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;
}