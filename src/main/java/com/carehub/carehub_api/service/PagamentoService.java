package com.carehub.carehub_api.service;

import com.carehub.carehub_api.dto.PagamentoRequest;
import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.model.Transacao;
import com.carehub.carehub_api.repository.AgendamentoRepository;
import com.carehub.carehub_api.repository.TransacaoRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    @Value("${STRIPE_SECRET_KEY}") // Lida com a variável de ambiente de segurança
    private String stripeSecretKey;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository; // ⬅️ NOVO REPOSITÓRIO INJETADO

    // Inicializa a chave secreta do Stripe
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Charge processarPagamento(PagamentoRequest request) throws StripeException {

        // 1. Validação do Agendamento
        Agendamento agendamento = agendamentoRepository.findById(request.getAgendamentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado."));

        if (request.getValorCentavos() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do pagamento deve ser positivo.");
        }

        // 2. Configura a Cobrança no Stripe
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getValorCentavos());
        chargeParams.put("currency", "brl");
        chargeParams.put("source", request.getToken());
        chargeParams.put("description", "Consulta CareHub: Agendamento ID " + request.getAgendamentoId());

        try {
            // 3. Executa a Cobrança
            Charge charge = Charge.create(chargeParams);

            // 🚨 4. PERSISTÊNCIA DA TRANSAÇÃO NO BANCO DE DADOS 🚨
            if (charge.getPaid()) {
                Transacao novaTransacao = new Transacao();
                novaTransacao.setAgendamento(agendamento);
                // Converte centavos para Reais (Double) para o DB
                novaTransacao.setValor(request.getValorCentavos() / 100.0);
                novaTransacao.setStatus("Pago");
                novaTransacao.setTransacaoId(charge.getId());
                novaTransacao.setDataTransacao(LocalDateTime.now()); // Define a data da transação

                transacaoRepository.save(novaTransacao); // Salva no DB
            }

            return charge;

        } catch (StripeException e) {
            // Trata erros de cartão recusado, saldo insuficiente, etc.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento Recusado: " + e.getMessage());
        }
    }
}