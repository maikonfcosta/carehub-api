package com.carehub.carehub_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO para representar um registro de pagamento no relatório.
 */
@Data
public class PagamentoRelatorioDTO {
    private Long id;
    private Long agendamentoId;
    private String pacienteNome;
    private LocalDateTime dataTransacao;
    private Double valor; // Valor em Reais (R$)
    private String status;
    private String transacaoId; // ID da transação no Stripe (ch_xxx)
}