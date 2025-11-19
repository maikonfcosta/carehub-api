package com.carehub.carehub_api.service.impl;

import com.carehub.carehub_api.model.Paciente;
import com.carehub.carehub_api.repository.PacienteRepository;
import com.carehub.carehub_api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.beans.BeanUtils;

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

    /**
     * NOVO: Atualiza um paciente existente.
     */
    @Override
    public Paciente atualizar(Long id, Paciente pacienteDetalhes) {
        // 1. Busca o paciente pelo ID
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para atualização."));

        // 2. Validação de CPF (se o CPF for alterado e já existir em outro paciente)
        Paciente pacienteComCpfIgual = pacienteRepository.findByCpf(pacienteDetalhes.getCpf());

        if (pacienteComCpfIgual != null && !pacienteComCpfIgual.getId().equals(pacienteExistente.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado em outro paciente.");
        }

        // 3. Copia as novas propriedades para a entidade existente (exclui o ID da cópia)
        // O BeanUtils copia os valores, exceto o ID, garantindo que a entidade correta seja atualizada.
        BeanUtils.copyProperties(pacienteDetalhes, pacienteExistente, "id");

        // 4. Salva (atualiza) a entidade
        return pacienteRepository.save(pacienteExistente);
    }

    /**
     * NOVO: Exclui um paciente pelo ID.
     */
    @Override
    public void excluir(Long id) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para exclusão."));

        // TODO: Adicionar lógica de verificação se há agendamentos vinculados antes de excluir.

        pacienteRepository.delete(pacienteExistente);
    }
}