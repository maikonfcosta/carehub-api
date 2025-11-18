package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Medico;
import com.carehub.carehub_api.service.impl.MedicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações com a entidade Medico.
 * Mapeado para o caminho base "/api/medicos".
 */
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoServiceImpl medicoService;

    /**
     * Cadastra um novo médico.
     * POST /api/medicos
     */
    @PostMapping
    public ResponseEntity<Medico> cadastrarMedico(@RequestBody Medico medico) {
        Medico novoMedico = medicoService.salvar(medico);
        return new ResponseEntity<>(novoMedico, HttpStatus.CREATED);
    }

    /**
     * Lista todos os médicos cadastrados.
     * GET /api/medicos
     */
    @GetMapping
    public List<Medico> listarTodos() {
        return medicoService.buscarTodos();
    }

    /**
     * Busca um médico por ID.
     * GET /api/medicos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }
}