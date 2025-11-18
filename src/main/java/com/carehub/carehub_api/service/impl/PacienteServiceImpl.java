package com.carehub.carehub_api.service.impl;

import com.carehub.carehub_api.model.Paciente;
import com.carehub.carehub_api.repository.PacienteRepository;
import com.carehub.carehub_api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Implementação da interface PacienteService, contendo a lógica de negócio.
 */
@Service // Indica ao Spring que esta é uma classe de Serviço
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Salva um paciente, aplicando a regra de validação de CPF.
     */
    @Override
    public Paciente salvar(Paciente paciente) {
        // 1. Regra de Negócio: Verificar se o CPF já está cadastrado
        Paciente pacienteExistente = pacienteRepository.findByCpf(paciente.getCpf());

        // Se o CPF já existir E o paciente que estamos tentando salvar for um novo (id == null) ou
        // se o ID do paciente existente for diferente do ID do paciente que está sendo atualizado (para evitar conflito na atualização)
        if (pacienteExistente != null && (paciente.getId() == null || !pacienteExistente.getId().equals(paciente.getId()))) {
            // Lança uma exceção com o status HTTP 409 (Conflict), pois o recurso (CPF) já existe.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado no sistema.");
        }

        // 2. Persistência: Se a validação passar, salva no banco de dados
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPorId(Long id) {
        // Usa Optional para lidar com a possibilidade do paciente não existir
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
    }
}