package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.service.impl.AgendamentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}