package com.carehub.carehub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Representa o agendamento de uma consulta entre um Paciente e um Médico.
 */
@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Paciente: Muitos Agendamentos para um Paciente
    @ManyToOne // Indica que a coluna fará referência à tabela Pacientes
    @JoinColumn(name = "paciente_id", nullable = false) // Nome da coluna FK no DB
    private Paciente paciente;

    // Relacionamento com Médico: Muitos Agendamentos para um Médico
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private LocalDateTime dataHora; // Data e hora da consulta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status = StatusAgendamento.PENDENTE;
}