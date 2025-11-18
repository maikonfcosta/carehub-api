package com.carehub.carehub_api.repository;

import com.carehub.carehub_api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de Repositório para a entidade Paciente.
 * Estende JpaRepository para herdar operações CRUD básicas.
 * * Parâmetros de JpaRepository<T, ID>:
 * T: O tipo da entidade (Paciente).
 * ID: O tipo da chave primária da entidade (Long).
 */
@Repository // Indica ao Spring que esta interface é um componente de acesso a dados
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Exemplo de um método customizado:
    // O Spring cria a query SQL automaticamente baseado no nome do método.
    // Isso será útil na funcionalidade "Busca" por CPF.
    Paciente findByCpf(String cpf);
}