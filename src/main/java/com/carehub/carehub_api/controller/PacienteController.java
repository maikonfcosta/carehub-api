package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Paciente;
import com.carehub.carehub_api.service.PacienteService; // Importar o Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * NOVO: Atualiza um paciente existente.
     * PUT /api/pacientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, @RequestBody Paciente pacienteDetalhes) {
        Paciente pacienteAtualizado = pacienteService.atualizar(id, pacienteDetalhes);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    /**
     * NOVO: Exclui um paciente pelo ID.
     * DELETE /api/pacientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluir(id);
        // Retorna status 204 No Content, que indica sucesso na exclusão.
        return ResponseEntity.noContent().build();
    }
}