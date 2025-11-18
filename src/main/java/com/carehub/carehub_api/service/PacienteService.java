package com.carehub.carehub_api.service;

import com.carehub.carehub_api.model.Paciente;
import java.util.List;

/**
 * Interface de Serviço para definir as operações de negócio da entidade Paciente.
 */
public interface PacienteService {

    /**
     * Salva ou atualiza um paciente no sistema.
     * Deve conter a lógica de validação (ex: CPF único).
     */
    Paciente salvar(Paciente paciente);

    /**
     * Busca todos os pacientes cadastrados.
     */
    List<Paciente> buscarTodos();

    /**
     * Busca um paciente pelo seu identificador único.
     */
    Paciente buscarPorId(Long id);
}