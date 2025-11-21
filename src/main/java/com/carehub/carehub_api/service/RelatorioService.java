package com.carehub.carehub_api.service;

import com.carehub.carehub_api.dto.PagamentoRelatorioDTO;
import com.carehub.carehub_api.model.Transacao;
import com.carehub.carehub_api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private TransacaoRepository transacaoRepository; // ⬅️ Injeta o repositório real

    /**
     * BUSCA REAL: Retorna o histórico de pagamentos lendo a tabela Transacoes.
     */
    public List<PagamentoRelatorioDTO> getHistoricoPagamentos() {
        // Busca todas as transações salvas no DB
        List<Transacao> transacoes = transacaoRepository.findAll();

        // Mapeia as entidades Transacao para o DTO de Relatório
        return transacoes.stream()
                .map(transacao -> {
                    PagamentoRelatorioDTO dto = new PagamentoRelatorioDTO();
                    dto.setId(transacao.getId());

                    // 🚨 Acesso aos dados do Agendamento e Paciente via relacionamento (Lazy/Eager Loading)
                    dto.setAgendamentoId(transacao.getAgendamento().getId());
                    dto.setPacienteNome(transacao.getAgendamento().getPaciente().getNomeCompleto());

                    dto.setDataTransacao(transacao.getDataTransacao());
                    dto.setValor(transacao.getValor());
                    dto.setStatus(transacao.getStatus());
                    dto.setTransacaoId(transacao.getTransacaoId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}