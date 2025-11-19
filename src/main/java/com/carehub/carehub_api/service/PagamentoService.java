package com.carehub.carehub_api.service;

import com.carehub.carehub_api.dto.PagamentoRequest;
import com.carehub.carehub_api.model.Agendamento;
import com.carehub.carehub_api.repository.AgendamentoRepository;
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

@Service
public class PagamentoService {

    @Value("${STRIPE_SECRET_KEY}") // ⬅️ LÊ A VARIÁVEL DE AMBIENTE DO RENDER
    private String stripeSecretKey;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Inicializa a chave secreta do Stripe assim que o serviço é carregado.
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    /**
     * Processa o pagamento via cartão usando o Token do Front-end.
     */
    public Charge processarPagamento(PagamentoRequest request) throws StripeException {

        // 1. Validações de Negócio
        Agendamento agendamento = agendamentoRepository.findById(request.getAgendamentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado."));

        if (request.getValorCentavos() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do pagamento deve ser positivo.");
        }

        // 2. Configura a Cobrança
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getValorCentavos()); // Valor em centavos
        chargeParams.put("currency", "brl"); // Moeda brasileira
        chargeParams.put("source", request.getToken()); // O token do cartão
        chargeParams.put("description", "Consulta CareHub: Agendamento ID " + request.getAgendamentoId());

        try {
            // 3. Executa a Cobrança no Stripe
            Charge charge = Charge.create(chargeParams);

            // 4. Se a cobrança for bem-sucedida, você pode atualizar o status do agendamento aqui
            // agendamento.setStatus(StatusAgendamento.PAGO);
            // agendamentoRepository.save(agendamento);

            return charge;

        } catch (StripeException e) {
            // Trata erros de cartão recusado, saldo insuficiente, etc.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento Recusado: " + e.getMessage());
        }
    }
}