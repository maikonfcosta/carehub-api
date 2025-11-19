package com.carehub.carehub_api.service;

import com.carehub.carehub_api.dto.PagamentoRelatorioDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class RelatorioService {

    /**
     * SIMULAÇÃO: Retorna uma lista mockada de pagamentos para fins de relatório.
     * Em produção, buscaria os dados de pagamentos na sua tabela de Transações ou no log do Stripe.
     */
    public List<PagamentoRelatorioDTO> getHistoricoPagamentos() {
        // Mock de dados transacionais
        PagamentoRelatorioDTO p1 = new PagamentoRelatorioDTO();
        p1.setId(1L);
        p1.setAgendamentoId(101L);
        p1.setPacienteNome("Lucas Palma Monteiro");
        p1.setDataTransacao(LocalDateTime.of(2025, 11, 15, 10, 30));
        p1.setValor(150.00);
        p1.setStatus("Pago");
        p1.setTransacaoId("ch_123ABC_TEST_PAGO");

        PagamentoRelatorioDTO p2 = new PagamentoRelatorioDTO();
        p2.setId(2L);
        p2.setAgendamentoId(102L);
        p2.setPacienteNome("Maikon Felipe Costa");
        p2.setDataTransacao(LocalDateTime.of(2025, 11, 14, 14, 0));
        p2.setValor(150.00);
        p2.setStatus("Pago");
        p2.setTransacaoId("ch_456DEF_TEST_PAGO");

        PagamentoRelatorioDTO p3 = new PagamentoRelatorioDTO();
        p3.setId(3L);
        p3.setAgendamentoId(103L);
        p3.setPacienteNome("Kevin Rafael de Moraes");
        p3.setDataTransacao(LocalDateTime.of(2025, 11, 18, 9, 0));
        p3.setValor(50.00);
        p3.setStatus("Recusado");
        p3.setTransacaoId("ch_789GHI_TEST_RECUSADO");

        return Arrays.asList(p1, p2, p3);
    }
}