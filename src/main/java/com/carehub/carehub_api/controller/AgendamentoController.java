package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.service.impl.AgendamentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.carehub.carehub_api.repository.AgendamentoRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

/**
 * Controlador REST para gerenciar operações de Agendamento.
 * Mapeado para o caminho base "/api/agendamentos".
 */
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoServiceImpl agendamentoService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    /**
     * Realiza um novo agendamento.
     * POST /api/agendamentos
     */
    @PostMapping
    public ResponseEntity<Agendamento> agendarConsulta(@RequestBody Agendamento agendamento) {
        Agendamento novoAgendamento = agendamentoService.salvar(agendamento);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }

    /**
     * Lista todos os agendamentos.
     * GET /api/agendamentos
     */
    @GetMapping
    public List<Agendamento> listarTodos() {
        return agendamentoService.buscarTodos();
    }

    @GetMapping
    public List<Agendamento> listarTodos(@RequestParam(required = false) String data) {
        if (data != null && !data.isEmpty()) {
            try {
                // Converte a string YYYY-MM-DD para um intervalo de tempo (início e fim do dia)
                LocalDate dataBusca = LocalDate.parse(data);
                LocalDateTime inicioDia = dataBusca.atStartOfDay();
                LocalDateTime fimDia = dataBusca.atTime(LocalTime.MAX);

                // Retorna a busca filtrada
                return agendamentoRepository.findByDataHoraBetween(inicioDia, fimDia);

            } catch (Exception e) {
                // Erro de parsing (Ex: data inválida)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido. Use YYYY-MM-DD.");
            }
        }
        // Se não houver data, retorna todos (comportamento padrão)
        return agendamentoService.buscarTodos();
    }
}