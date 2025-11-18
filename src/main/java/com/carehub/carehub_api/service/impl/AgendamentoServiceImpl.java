package com.carehub.carehub_api.service.impl;

import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.model.Medico;
import com.carehub.carehub_api.model.Paciente;
import com.carehub.carehub_api.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AgendamentoServiceImpl {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Injetamos os serviços de Paciente e Médico para validar se eles existem antes de agendar.
    @Autowired
    private PacienteServiceImpl pacienteService;
    @Autowired
    private MedicoServiceImpl medicoService;

    /**
     * Realiza o agendamento de uma consulta, aplicando a regra de negócio de horário.
     */
    public Agendamento salvar(Agendamento agendamento) {

        // 1. Validação de Existência: Garante que o Paciente e o Médico existem no DB.
        // Se não existirem, os métodos buscarPorId() lançarão ResponseStatusException (404 NOT FOUND).
        // Isso garante a integridade referencial dos dados antes de salvar.
        Paciente paciente = pacienteService.buscarPorId(agendamento.getPaciente().getId());
        Medico medico = medicoService.buscarPorId(agendamento.getMedico().getId());

        // Atribui as entidades completas ao agendamento (garantindo que o JPA as gerencie)
        agendamento.setPaciente(paciente);
        agendamento.setMedico(medico);

        // 2. Regra de Negócio: Verificar conflito de horário para o Médico.
        boolean conflito = agendamentoRepository.findByMedicoAndDataHora(medico, agendamento.getDataHora()).isPresent();

        if (conflito) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O médico já possui uma consulta agendada para este horário.");
        }

        // 3. Persistência: Se tudo estiver ok, salva o agendamento
        return agendamentoRepository.save(agendamento);
    }

    /**
     * Retorna todos os agendamentos.
     */
    public List<Agendamento> buscarTodos() {
        return agendamentoRepository.findAll();
    }
}