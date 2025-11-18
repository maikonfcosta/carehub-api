package com.carehub.carehub_api.service.impl;

import com.carehub.carehub_api.model.Medico;
import com.carehub.carehub_api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Serviço de negócio para a entidade Médico.
 * Contém a lógica de validação de CRM único.
 */
@Service
public class MedicoServiceImpl {

    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * Salva ou atualiza um médico, aplicando a validação de CRM.
     */
    public Medico salvar(Medico medico) {
        // Validação: Verificar se o CRM já está cadastrado
        Medico medicoExistente = medicoRepository.findByCrm(medico.getCrm());

        if (medicoExistente != null && (medico.getId() == null || !medicoExistente.getId().equals(medico.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado no sistema.");
        }

        return medicoRepository.save(medico);
    }

    /**
     * Busca todos os médicos.
     */
    public List<Medico> buscarTodos() {
        return medicoRepository.findAll();
    }

    /**
     * Busca um médico por ID.
     */
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
    }
}