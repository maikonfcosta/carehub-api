package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Paciente;
import com.carehub.carehub_api.service.PacienteService; // Importar o Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    // AGORA INJETAMOS O SERVIÇO, NÃO MAIS O REPOSITÓRIO
    @Autowired
    private PacienteService pacienteService;

    /**
     * Endpoint para cadastrar um novo paciente (agora chama o Service).
     * Mapeia requisições HTTP POST para /api/pacientes.
     */
    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody Paciente paciente) {
        // A lógica de validação (CPF único) está agora no Service
        Paciente novoPaciente = pacienteService.salvar(paciente);
        return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar todos os pacientes (agora chama o Service).
     * Mapeia requisições HTTP GET para /api/pacientes.
     */
    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteService.buscarTodos();
    }

    // NOVO: Endpoint para buscar paciente por ID.
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }
}