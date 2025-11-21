package com.carehub.carehub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o Agendamento (Chave Estrangeira)
    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false, unique = true)
    private Agendamento agendamento;

    @Column(nullable = false)
    private LocalDateTime dataTransacao = LocalDateTime.now();

    @Column(nullable = false)
    private Double valor; // Valor em Reais (R$)

    @Column(nullable = false)
    private String status; // Ex: "Pago", "Recusado"

    @Column(nullable = true)
    private String transacaoId; // ID da transação no Stripe (ch_xxx)
}