package com.carehub.carehub_api.service.impl;

import com.carehub.carehub_api.model.Medico;
import com.carehub.carehub_api.repository.MedicoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedicoServiceImpl {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico salvar(Medico medico) {
        Medico medicoExistente = medicoRepository.findByCrm(medico.getCrm());

        if (medicoExistente != null && (medico.getId() == null || !medicoExistente.getId().equals(medico.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado no sistema.");
        }

        return medicoRepository.save(medico);
    }

    /**
     * NOVO: Atualiza um médico existente.
     */
    public Medico atualizar(Long id, Medico medicoDetalhes) {
        // 1. Busca o médico pelo ID
        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado para atualização."));

        // 2. Validação de CRM
        Medico medicoComCrmIgual = medicoRepository.findByCrm(medicoDetalhes.getCrm());

        if (medicoComCrmIgual != null && !medicoComCrmIgual.getId().equals(medicoExistente.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado em outro médico.");
        }

        // 3. Copia as novas propriedades para a entidade existente
        BeanUtils.copyProperties(medicoDetalhes, medicoExistente, "id");

        // 4. Salva (atualiza) a entidade
        return medicoRepository.save(medicoExistente);
    }

    /**
     * NOVO: Exclui um médico pelo ID.
     */
    public void excluir(Long id) {
        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado para exclusão."));

        // Lógica: Exclui o médico
        medicoRepository.delete(medicoExistente);
    }

    public List<Medico> buscarTodos() {
        return medicoRepository.findAll();
    }

    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
    }
}