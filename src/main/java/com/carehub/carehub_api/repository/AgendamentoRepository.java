package com.carehub.carehub_api.repository;

import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    /**
     * Consulta customizada: Verifica se já existe um agendamento para um dado médico
     * em uma data/hora específica (conflito de horário).
     * O Spring constrói o SQL: SELECT * FROM agendamentos WHERE medico_id = ? AND data_hora = ?
     */
    Optional<Agendamento> findByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);

    /**
     * Consulta para buscar agendamentos por médico (útil para a agenda do médico)
     */
    List<Agendamento> findByMedicoOrderByDataHoraAsc(Medico medico);
    List<Agendamento> findByDataHoraBetween(LocalDateTime start, LocalDateTime end);
}