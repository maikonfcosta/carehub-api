package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.model.Medico;
import com.carehub.carehub_api.service.impl.MedicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoServiceImpl medicoService;

    @PostMapping
    public ResponseEntity<Medico> cadastrarMedico(@RequestBody Medico medico) {
        Medico novoMedico = medicoService.salvar(medico);
        return new ResponseEntity<>(novoMedico, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Medico> listarTodos() {
        return medicoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }

    /**
     * NOVO: Atualiza um médico existente.
     * PUT /api/medicos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualizarMedico(@PathVariable Long id, @RequestBody Medico medicoDetalhes) {
        Medico medicoAtualizado = medicoService.atualizar(id, medicoDetalhes);
        return ResponseEntity.ok(medicoAtualizado);
    }

    /**
     * NOVO: Exclui um médico pelo ID.
     * DELETE /api/medicos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMedico(@PathVariable Long id) {
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}