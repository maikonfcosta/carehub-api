package com.carehub.carehub_api.dto;

import lombok.Data;

@Data
public class PagamentoRequest {
    private String token; // Token gerado pelo Stripe.js no Front-end (representa os dados do cartão)
    private Long agendamentoId;
    private Long valorCentavos; // O valor da consulta em centavos (ex: R$ 100,00 -> 10000)
}