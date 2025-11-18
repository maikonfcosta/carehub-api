package com.carehub.carehub_api.repository;

import com.carehub.carehub_api.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de Repositório para a entidade Medico.
 * Fornece operações CRUD básicas.
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Método para buscar um médico pelo CRM, útil para validação de unicidade.
    Medico findByCrm(String crm);

    // Podemos adicionar futuramente um método para buscar médicos por especialidade.
}